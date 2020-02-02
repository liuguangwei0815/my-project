/**
 * 
 */
package com.my.security.servive.impl;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.security.bean.AccountInfo;
import com.my.security.mapper.AccountInfoMapper;
import com.my.security.openfeign.Bank2AccountClient;
import com.my.security.servive.AccountService;

import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liuwei
 *
 */
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountInfoMapper accountInfoMapper;
	@Autowired
	private Bank2AccountClient bank2AccountClient;

	@Override
	@Transactional
	@GlobalTransactional // seata 开启全局事务
	public void updateBank1Account(AccountInfo obj) {

		log.info("bank1 开启全局事务XID：{}", RootContext.getXID());

		accountInfoMapper.updateAccountInfo(new AccountInfo("2", obj.getAccountBalance()));

		log.info("feign rest 请求 bank2");

		String result = bank2AccountClient.updateAccount(
				new AccountInfo("1", obj.getAccountBalance(),obj.getAccountName()));
		
		if(StringUtils.equals(result, "rollback")) {
			throw new RuntimeException("调用李四服务器失败");
		}
		
		if(StringUtils.equals(obj.getAccountName(), "2")) {
			throw new RuntimeException("人为制造bank1 异常");
		}
	}

}
