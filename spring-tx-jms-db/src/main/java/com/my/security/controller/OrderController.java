package com.my.security.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.my.security.service.CustomerService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	

	@PostMapping
	public void create(@RequestBody String msg) {
		jmsTemplate.convertAndSend("customer:jms:send",msg);
	}

	@GetMapping
	public Map<String, Object> querry() {
		return customerService.getInfo();
	}

}
