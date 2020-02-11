package com.my.security.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class AnnotiatinAspect {

	@Autowired
	private HttpServletRequest request;


	@Around("@annotation(com.my.security.aop.LogAnnotation)")
	public Object around(ProceedingJoinPoint point) throws Throwable {

		log.info("开始执行aop 获取 URL：{},获取IP:{}",request.getRequestURI(),request.getRemoteAddr());
		
		Object obj = point.proceed();
		
		log.info("结束执行 aop 获取返回值;{}",obj);

		return obj;
	}
	
	/*
	 * @Pointcut("@annotation(com.my.security.aop.LogAnnotation)") public void
	 * pointcut() { }
	 * 
	 * @Before("pointcut()") public void before(Pointcut pc) {
	 * log.info("回去URL：{},获取IP:{}",request.getRequestURI(),request.getRemoteAddr());
	 * }
	 * 
	 * 
	 * @AfterReturning(pointcut="pointcut()",returning = "ret") public void
	 * before(Object ret) { log.info("获取返回值：{}",ret); }
	 */

}
