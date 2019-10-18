package com.my.security;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
public class SecurityDemoApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(SecurityDemoApplication.class).web(WebApplicationType.SERVLET).run(args);
	}

}
