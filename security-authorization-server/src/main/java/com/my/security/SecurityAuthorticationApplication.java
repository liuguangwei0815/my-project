package com.my.security;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
public class SecurityAuthorticationApplication {
	
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(SecurityAuthorticationApplication.class).web(WebApplicationType.SERVLET).run(args);
	}

}
