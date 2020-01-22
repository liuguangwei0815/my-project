package com.my.security.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.security.entity.Order;
import com.my.security.service.CustomerService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private CustomerService customerService;

	@PostMapping
	public Order create(@RequestBody Order order) {
		return customerService.creaeteOrder(order);
	}

	@GetMapping
	public Map<String, Object> querry(Long id) {
		return customerService.getInfo(id);
	}

}
