package com.my.security.browser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.my.security.browser.session.SessionConcurrentStategy;
import com.my.security.browser.session.SessionExprireStategy;
import com.my.security.properites.SecurityProperties;

/**
 * Security Bean config
 * @author Administrator
 *
 */
@Configuration
public class BrowserSecurityBeanConfig {
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Bean
	@ConditionalOnMissingBean(name = "sessionConcurrentStategy")
	public SessionConcurrentStategy sessionConcurrentStategy() {
		return new  SessionConcurrentStategy(securityProperties.getBrowser().getSessionInvalideUrl());
	}
	
	@Bean
	@ConditionalOnMissingBean(name = "sessionExprireStategy")
	public SessionExprireStategy sessionExprireStategy() {
		return new  SessionExprireStategy(securityProperties.getBrowser().getSessionInvalideUrl());
	}
	

}
