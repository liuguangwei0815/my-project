package com.my.security.vaidata.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

@Component("smsAndImageValidataFilterConfig")
public class SmsAndImageValidataFilterConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>{
	
	@Autowired
	private SmsAndImageValidataFilter smsAndImageValidataFilter;
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(smsAndImageValidataFilter, AbstractPreAuthenticatedProcessingFilter.class);
	}

}
