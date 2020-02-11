/**
 * 
 */
package com.my.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.my.security.ioc.getbean.BeanAwareCode;
import com.my.security.ioc.getbean.BeanAwareCode2;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuwei
 *
 */
@SpringBootApplication
@Configuration
@Slf4j
public class MianshiCodeApplaiction {
	
	
	@Bean
	public BeanAwareCode zhangs() {
		return new BeanAwareCode("zhangsang");
	}
	@Bean
	public BeanAwareCode2 lis() {
		return new BeanAwareCode2("ls");
	}


	/**
	 * 测试 主要为了可以获取对应的属性，Aware 接口 否则无法获取自身spring给予的属性
	 *  比如beanId 想获取这个 必须得通过实现aware 接口自身感知 否则你获取不了 
	 *其实就是通过实现了这个接口可以有相对于的实现方法而已
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext contx = SpringApplication.run(MianshiCodeApplaiction.class, args);
//		BeanAwareCode zhang = contx.getBean(BeanAwareCode.class);
//		BeanAwareCode2 ls = contx.getBean(BeanAwareCode2.class);
//		//zhang :BeanAwareCode(id=zhangs, name=zhangsang)
//		log.info("zhang :{}",zhang.toString());
//		//ls :BeanAwareCode2(id=null, name=ls)
//		log.info("ls :{}",ls.toString());
	}

}
