package com.my.security;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
public class SecuritySSOClient1Application {
	
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(SecuritySSOClient1Application.class).web(WebApplicationType.SERVLET).run(args);
	}

}
