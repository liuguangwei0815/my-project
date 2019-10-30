package com.my.security.validata;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.my.security.service.HelloService;

import lombok.extern.slf4j.Slf4j;

/**
 * 校验逻辑
 * @author Administrator
 *
 */
@Slf4j
public class ContraintValidator implements ConstraintValidator<MyContraint, Object>{

	
	@Autowired
	private HelloService helloService;
	
	
	@Override
	public void initialize(MyContraint constraintAnnotation) {
		log.info("validator init......");
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		log.info("校验获取值：{}",value);
		helloService.getee(value);
		return true;
	}

}
