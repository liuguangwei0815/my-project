package com.my.security.vaidata.code.processor;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.my.security.vaidata.code.ImageCodeException;

/**
 * 查找实际处理对象
 * @author liuwei
 *
 */
@Component("validataProcessorHolder")
public class ValidataProcessorHolder {
	
	//依赖查找方法
	@Autowired
	private Map<String,ValidataProcessor> validataProcessors;
	
	
	public ValidataProcessor findValidataProcessorByType(String type) {
		String  beanName = type.trim().toLowerCase()+ValidataProcessor.class.getSimpleName();
		ValidataProcessor bean = validataProcessors.get(beanName);
		if(bean==null)
			throw new ImageCodeException("无法找到对应的bean");
		return bean;
	}
	
	

}
