package com.my.security.controller;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {
	
	private RestTemplate restTemplate = new RestTemplate();
	
	private static final String RPURL = "http://localhost:7023/";
	
	@PostMapping
	public Order getOrder(@Valid @RequestBody Order order,BindingResult error) {
		if(error.hasErrors()) {
			error.getAllErrors().stream().map(e->buildError(e)).findFirst();
		}
		PriceInfo pi = restTemplate.getForObject(RPURL+"price/"+order.getProductId(), PriceInfo.class);
		log.info("获取产品id：{},价格：{}",order.getProductId(),pi.getPrice());
		return  order;
	}

	private Object buildError(ObjectError e) {
		throw new RuntimeException(e.getDefaultMessage());
	}
}
