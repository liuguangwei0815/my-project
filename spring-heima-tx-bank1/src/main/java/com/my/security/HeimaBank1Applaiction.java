/**
 * 
 */
package com.my.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author liuwei
 *
 */
@SpringBootApplication
@EnableEurekaClient//开启注册服务
@EnableFeignClients
@EnableHystrix//开启Hystrix
public class HeimaBank1Applaiction {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(HeimaBank1Applaiction.class, args);
	}

}
