/**
 * 
 */
package com.my.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author liuwei
 *
 */
@SpringBootApplication
@EnableEurekaClient//开启注册服务
@EnableFeignClients
public class HeimaBank2Applaiction {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(HeimaBank2Applaiction.class, args);
	}

}
