/**
 * 
 */
package com.my.security.ioc.getbean;

import org.springframework.beans.factory.BeanNameAware;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liuwei
 *
 */
@Data
@Slf4j
public class BeanAwareCode implements BeanNameAware {

	private String id;
	private String name;

	@Override
	public void setBeanName(String beanName) {
		log.info("第四执行Aware 获取自有  属性 :{}",name);
		this.id = beanName;
	}

	public BeanAwareCode() {
	}

	public BeanAwareCode(String name) {
		this.name = name;
	}

}
