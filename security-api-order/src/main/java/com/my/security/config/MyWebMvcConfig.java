/**
 * 
 */
package com.my.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.my.security.interceptor.MyPrometheusInterceptor;

/**
 * @author liuwei
 * 配置metric 拦截器生效
 */
@Configuration
public class MyWebMvcConfig extends WebMvcConfigurationSupport{

	
	
	@Autowired
	private MyPrometheusInterceptor myPrometheusInterceptor;
	
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(myPrometheusInterceptor).addPathPatterns("/**");
	}
	
}
