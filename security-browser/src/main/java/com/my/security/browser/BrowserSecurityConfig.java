package com.my.security.browser;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configurable
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,proxyTargetClass = true)
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter{

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//认证方式
		http.formLogin()
		//basic 认证
		//http.httpBasic()
		//错误之后的请求访问路径
		.loginPage("/authentication/require")
		//提交登录页认证的设置 action=""
		.loginProcessingUrl("/authentication/form")
		.and()
		//授权
		.authorizeRequests()
		.antMatchers("/authentication/require").permitAll()
		.antMatchers("/login-in.html").permitAll()
		.anyRequest()
		.authenticated()
		.and().csrf().disable()
		;
	}
	
}
