package com.my.security.browser;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configurable
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter{

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//认证
		http.formLogin()
		//basic 认证
		//http.httpBasic()
		.and()
		//授权
		.authorizeRequests()
		.anyRequest()
		.authenticated();
	}
	
}
