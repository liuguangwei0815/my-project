package com.my.security.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import io.prometheus.client.Counter;
import io.prometheus.client.Summary;

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
	@Autowired
	private Summary summary;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		request.setAttribute("summaryStartTime", new Date().getTime());
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 设置值，然后inc 添加一个
		counter.labels(request.getRequestURI(), request.getMethod(), String.valueOf(response.getStatus())).inc();
		// 设置服务的响应时间
		summary.labels(request.getRequestURI(), request.getMethod(), String.valueOf(response.getStatus()))
				.observe(new Date().getTime() - (long) request.getAttribute("summaryStartTime"));
	}

}
