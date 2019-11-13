package com.my.security.browser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.my.security.properites.SecurityProperties;

import lombok.extern.slf4j.Slf4j;

@Configurable
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
@Slf4j
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("获取配置文件的登录页面:{}", securityProperties.getBrowser().getLoginpage());
		// 认证方式
		http.formLogin()
				// basic 认证
				// http.httpBasic()
				// 错误之后的请求访问路径
				.loginPage("/require")
				// 提交登录页认证的设置 action=""
				.loginProcessingUrl("/authentication/form").and()
				// 授权
				.authorizeRequests().antMatchers("/authentication/require").permitAll()
				.antMatchers("/login-demo.html", "/login-simple.html").permitAll()
				.anyRequest().authenticated().and().csrf().disable();

		// http.antMatcher("/**").authorizeRequests().anyRequest().permitAll();

	}

}
