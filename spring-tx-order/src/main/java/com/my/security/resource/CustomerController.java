package com.my.security.resource;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	
	@GetMapping
	public List<CustomerOrder> createincode(HttpServletRequest request) {
		return (List<CustomerOrder>) customorResposity.findAll();
	}
	

}
