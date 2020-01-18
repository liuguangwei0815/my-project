package com.my.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.my.security.bean.Customer;
import com.my.security.dao.CustomorResposity;

@Service
public class CustomorAnnotiationServiceImpl{
	
	@Autowired
	private CustomorResposity customorResposity;
	@Autowired
	private JmsTemplate jmsTemplate;

	@Transactional
	public Customer create(Customer obj) {
		
		customorResposity.save(obj);
		jmsTemplate.convertAndSend("customer:msg:aaa", JSON.toJSONString(obj));
		
		if(obj.getUsername().equals("jojo")) {
			throw new RuntimeException("异常。。。。。。");
		}
		
		return obj;
	}


}
