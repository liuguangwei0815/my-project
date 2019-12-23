package com.my.security;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextListener;

@SpringBootApplication
@RestController
@EnableOAuth2Sso
public class SecuritySSOClient1Application {

	@GetMapping("/user/me")
	public Authentication getUserMe(Authentication user) {
		return user;
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(SecuritySSOClient1Application.class).web(WebApplicationType.SERVLET).run(args);
	}

	@Bean
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}

}
