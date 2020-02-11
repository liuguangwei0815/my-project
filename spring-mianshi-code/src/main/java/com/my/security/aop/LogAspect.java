/**
 * 
 */
package com.my.security.aop;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuwei
 *
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

	@Autowired
	private HttpServletRequest request;

	/**
	 * 切入到哪里 切入到任何返回值的 下的com.my.security 下的所有类以patternaop 开头的任何方法名的任何参数
	 */
	@Pointcut("execution(public * com.my.security..*.patternaop*(..))")
	public void log() {
	}

	@Before("log()")
	public void Before(JoinPoint point) {
		log.info("获取URI 地址：{}，IP：{}", request.getRequestURI(), request.getRemoteAddr());
		log.info("类名：{}", point.getClass().getSimpleName());
		Arrays.stream(point.getArgs()).forEach(e -> {
			log.info("参数：{}", e);
		});
	}
	
	@AfterReturning(pointcut="log()",returning="ret")
	public void after(Object ret) {
		log.info("aop after 获取返回值：{}", ret);
	}
	
	
	@AfterThrowing(pointcut = "log()",throwing = "ex")
	public void afterthrowing(Throwable ex) {
		log.info("aop afterthrowing 异常捕获：{}", ex.getMessage());
	}
	

}
