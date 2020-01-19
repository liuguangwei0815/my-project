package com.my.security.resource;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.my.security.bean.CustomerOrder;
import com.my.security.dao.CustomorResposity;
import com.my.security.service.impl.CustomorAnnotiationServiceImpl;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomorAnnotiationServiceImpl customorAnnotiationServiceImpl;
	@Autowired
	private CustomorResposity customorResposity;

	@PostMapping
	public CustomerOrder create(@RequestBody CustomerOrder obj,HttpServletRequest request) {
		return customorAnnotiationServiceImpl.create(obj);
	}

	
	@GetMapping("/my/{id}")
	public String  getOrderInfoById(@PathVariable Long id) {
		CustomerOrder order = new CustomerOrder();
		order.setId(33l);
		order.setAmount(new BigDecimal(100));
		order.setDetails("我们的");
		order.setTitle("订单");
		return JSON.toJSONString(order);
	}
	
	@GetMapping
	public List<CustomerOrder> createincode(HttpServletRequest request) {
		return (List<CustomerOrder>) customorResposity.findAll();
	}
	

}
