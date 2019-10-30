package com.my.security.service.impl;

import org.springframework.stereotype.Service;

import com.my.security.service.HelloService;

@Service
public class HelloServiceImpl implements HelloService {
	
	@Override
	public void getee(Object name) {
		System.out.println("hello get===> "+name);
	}

}
