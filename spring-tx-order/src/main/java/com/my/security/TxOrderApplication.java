package com.my.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class TxOrderApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(TxOrderApplication.class,args);
	}
	
	@GetMapping
	public String helloword() {
		
		return "hello world";
	}
	
	
	

}
