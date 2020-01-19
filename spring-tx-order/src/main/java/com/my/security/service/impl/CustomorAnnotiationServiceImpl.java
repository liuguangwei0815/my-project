package com.my.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.security.bean.CustomerOrder;
import com.my.security.dao.CustomorResposity;

@Service
public class CustomorAnnotiationServiceImpl{
	
	@Autowired
	private CustomorResposity customorResposity;

	@Transactional
	public CustomerOrder create(CustomerOrder obj) {
		
		customorResposity.save(obj);
		
		return obj;
	}


}
