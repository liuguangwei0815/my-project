package com.my.security.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import io.prometheus.client.Counter;

/**
 * 定义拦截器配置 为prometheus 的metric 赋值
 * 
 * @author liuwei
 *
 */
@Component
public class MyPrometheusInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private Counter counter;

	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//设置值，然后inc 添加一个
		counter.labels(request.getRequestURI(), request.getMethod(), String.valueOf(response.getStatus())).inc();;
	}


	//并发处理启动后
	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

}
