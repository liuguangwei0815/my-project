package com.my.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@SpringBootApplication
@EnableEurekaClient
@RestController
@EnableHystrix//开始Hystrix// 网关不用开启这个Hystrix 因为网关本来内部就已经使用了Hystrix 所以不需要  这里因为是个普通项目 所以需要引用
public class TxPoxyApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(TxPoxyApplication.class,args);
	}
	
	@GetMapping
	@HystrixCommand//开启Hystrix 进行监控
	public String helloword() {
		
		return "hello world";
	}
	
	
	

}
