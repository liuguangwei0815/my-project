package com.my.security;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
public class SecuritySSOClient2Application {
	
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(SecuritySSOClient2Application.class).web(WebApplicationType.SERVLET).run(args);
	}

}
