/**
 * 
 */
package com.my.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author liuwei
 *
 */
@SpringBootApplication
@EnableEurekaServer//开启注册服务
public class HeimaEurakaApplaiction {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(HeimaEurakaApplaiction.class, args);
	}

}
