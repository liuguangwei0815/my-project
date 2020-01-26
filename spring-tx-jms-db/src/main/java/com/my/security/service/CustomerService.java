/**
 * 
 */
package com.my.security.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.security.dao.CustomorResposity;
import com.my.security.entity.Customer;
import com.my.security.entity.Order;

/**
 * @author liuwei
 *
 */
@Service
public class CustomerService {

	@Autowired
	private CustomorResposity customorResposity;
	@Autowired
	private JmsTemplate jmsTemplate;

	@Transactional
	@JmsListener(destination = "customer:jms:send")
	public void creaeteOrder(String msg) {

		Customer cs = new Customer();
		cs.setUsername(msg);
		cs.setDeposit(new BigDecimal(100));
		customorResposity.save(cs);
		if (StringUtils.contains(msg, "error1")) {// 如果这里异常了 这里会会馆 user
			throw new RuntimeException("user update error");
		}

		jmsTemplate.convertAndSend("customer:jms", "send:" + msg);

		if (StringUtils.contains(msg, "error2")) {// 如果这里异常了 这里会会馆 user
			throw new RuntimeException("user update error");
		}
		
	}

	public Map<String, Object> getInfo() {
		Map<String, Object> result = new HashMap<String, Object>();
		jmsTemplate.setReceiveTimeout(2000);
		result.put("msg", jmsTemplate.receiveAndConvert("customer:jms"));
		return result;
	}

}
