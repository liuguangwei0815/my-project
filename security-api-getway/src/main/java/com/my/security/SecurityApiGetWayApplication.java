package com.my.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy//开启网关
public class SecurityApiGetWayApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SecurityApiGetWayApplication.class);
	}

}
