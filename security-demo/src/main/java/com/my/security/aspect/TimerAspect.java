package com.my.security.aspect;

import java.util.Arrays;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 切片
 * 
 * @author Administrator
 *
 */
@Component
@Aspect
@Slf4j
public class TimerAspect {

	@Around("execution(* com.my.security.controller.*.*(..))")
	public Object timerAspectHandler(ProceedingJoinPoint pjp) throws Throwable {
		log.info("aspect start...");
		log.info("表达式切片拦截{}.{}方法", pjp.getTarget().getClass().getName(),pjp.getSignature().getName());
		Arrays.stream(pjp.getArgs()).forEach(
				e -> log.info("参数:{}", e));
		long start = new Date().getTime();
		Object obj = pjp.proceed();
		log.info("aspect 耗时:{}", new Date().getTime() - start);
		log.info("aspect end...");
		return obj;
	}

}
