package com.my.security.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.my.security.filter.TimerFilter;
import com.my.security.interceptor.TimerInterceptor;

/**
 * 引入第三方filter 或者自定义filter
 * @author Administrator
 *
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport{
	
	@Autowired
	private TimerInterceptor timerInterceptor;
	
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(timerInterceptor);
	}
	
	/**
	 * 拦截器 拦截器不能通过doFilter 不能获取请求的方法和类 想要获取必须通过intercept 的HandlerMethed获取
	 * @return
	 */
	@Bean
	public FilterRegistrationBean<TimerFilter> timerFilter(){
		
		FilterRegistrationBean<TimerFilter> bean = new FilterRegistrationBean<TimerFilter>();
		bean.setFilter(new TimerFilter());
		List<String> url = new ArrayList<>();
		url.add("/*");
		bean.setUrlPatterns(url);
		return bean;
	}

}
