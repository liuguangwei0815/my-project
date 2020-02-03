/**
 * 
 */
package com.my.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author liuwei
 *
 */
@SpringBootApplication
@EnableEurekaClient//开启注册服务
@EnableFeignClients
//如果定了 这个 那么spring 就会去这个里找标识位@controller @server 等组件 ，如果不写这个注解 那么他默认会全部pacakge 去找 这里定义去找hmily去找
@ComponentScan({"com.my.security","org.dromara.hmily"})
public class HeimaBank2tccApplaiction {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(HeimaBank2tccApplaiction.class, args);
	}

}
