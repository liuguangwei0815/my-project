/**
 * 
 */
package com.my.security.resource;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @author liuwei
 * 暴露的接口不需要身份验证
 */
@Configuration
public class ActualConfig extends ResourceServerConfigurerAdapter {

	
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId("order-server");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()//将暴露的接口都放开 为了数据prometheus接口保留出来
		.anyRequest().authenticated();
	}

	
	
}
