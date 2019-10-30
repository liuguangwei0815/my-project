package com.my.security.aspect;
/**
 * 切片 anniton
 * @author Administrator
 *
 */

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
@Component
public class TimerAspectAnnotiation {

	@Around(value = "@annotation(com.my.security.aspect.TimerAntiontation)")
	public Object annotionAspect(ProceedingJoinPoint pjp) throws Throwable {
		log.info("注解切片拦截获取{}.{}", pjp.getTarget().getClass().getName(), pjp.getSignature().getName());
		Object obj = pjp.proceed();
		return obj;
	}

}
