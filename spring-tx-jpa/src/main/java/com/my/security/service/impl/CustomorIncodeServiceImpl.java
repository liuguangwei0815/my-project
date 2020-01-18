package com.my.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.my.security.bean.Customer;
import com.my.security.dao.CustomorResposity;

@Service
public class CustomorIncodeServiceImpl {

	@Autowired
	private CustomorResposity customorResposity;
	@Autowired
	private PlatformTransactionManager platformTransactionManager;

	public Customer create(Customer obj) {
		DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
		definition.setIsolationLevel(Isolation.SERIALIZABLE.value());
		definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);// Support:如果有事物那么也加入否则不加入，还有个notSupport ..request 有事物加入 否则新建
																						// 标识不需要事物执行，如果之前有事物那么
		TransactionStatus status = platformTransactionManager.getTransaction(definition);
		try {
			customorResposity.save(obj);
			throwErro();
			platformTransactionManager.commit(status);
			return obj;
		} catch (Exception e) {
			platformTransactionManager.rollback(status);
		}
		return null;
	}
	private void throwErro() {

		throw new RuntimeException("some error");
	}

}
