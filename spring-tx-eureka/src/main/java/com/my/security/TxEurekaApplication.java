package com.my.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableEurekaServer//开启注册服务
@EnableHystrixDashboard//开启HyStrix bashboard
public class TxEurekaApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(TxEurekaApplication.class,args);
	}
	

}
