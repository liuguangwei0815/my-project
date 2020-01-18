package com.my.security.resource;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.security.bean.Customer;
import com.my.security.dao.CustomorResposity;
import com.my.security.service.impl.CustomorAnnotiationServiceImpl;
import com.my.security.service.impl.CustomorIncodeServiceImpl;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomorAnnotiationServiceImpl customorAnnotiationServiceImpl;
	@Autowired
	private CustomorIncodeServiceImpl customorIncodeServiceImpl;
	@Autowired
	private CustomorResposity customorResposity;

	@PostMapping
	public Customer create(@RequestBody Customer obj,HttpServletRequest request) {
		return customorAnnotiationServiceImpl.create(obj);
	}

	@PostMapping("/incode")
	public Customer createincode(@RequestBody Customer obj,HttpServletRequest request) {
		return customorIncodeServiceImpl.create(obj);
	}
	
	@GetMapping
	public List<Customer> createincode(HttpServletRequest request) {
		return (List<Customer>) customorResposity.findAll();
	}
	

}
