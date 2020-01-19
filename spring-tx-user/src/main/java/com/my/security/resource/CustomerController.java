package com.my.security.resource;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.my.security.bean.Customer;
import com.my.security.dao.CustomorResposity;
import com.my.security.dto.CustomerOrder;
import com.my.security.openfeign.OrderFeignClient;
import com.my.security.service.impl.CustomorAnnotiationServiceImpl;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomorAnnotiationServiceImpl customorAnnotiationServiceImpl;
	@Autowired
	private CustomorResposity customorResposity;
	@Autowired
	private OrderFeignClient orderFeignClient;
	

	@PostMapping
	public Customer create(@RequestBody Customer obj,HttpServletRequest request) {
		return customorAnnotiationServiceImpl.create(obj);
	}

	
	@GetMapping("/getOrderInfo")
	public CustomerOrder getOrderInfo(HttpServletRequest request) {
		return JSON.parseObject(orderFeignClient.getOrderInfoById(1l), CustomerOrder.class);
	}
	
	@GetMapping
	public List<Customer> createincode(HttpServletRequest request) {
		return (List<Customer>) customorResposity.findAll();
	}
	

}
