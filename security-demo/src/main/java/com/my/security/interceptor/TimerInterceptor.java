package com.my.security.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TimerInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info("interceptor preHandle ...");
		long startTime = new Date().getTime();
		request.setAttribute("startTime", startTime);
		HandlerMethod mhl = ((HandlerMethod) handler);
		log.info("进入了：{}.{}方法", mhl.getBean().getClass().getName(), mhl.getMethod().getName());
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.info("interceptor postHandle ...");
		// 耗时
		log.info("interceptor postHandle ...耗时：{}",
				new Date().getTime() - (long)request.getAttribute("startTime"));
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.info("interceptor afterCompletion ...");
		log.info("异常:{}",ex);
		// 耗时
		log.info("interceptor afterCompletion ...耗时：{}",
				new Date().getTime() - (long)request.getAttribute("startTime"));
	}

}
