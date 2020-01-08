package com.my.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启了这个注解才能开启授权的注解比如@pre
public class SecurityApiOrderApplication {
	
	/**
	 * spring oauth2 已经帮我们提供了这个工具 ，会将jwt字符串 放到我们的请求头上去
	 * @param resource
	 * @param context
	 * @return
	 */
	@Bean
	public OAuth2RestTemplate oAuth2RestTemplate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext context) {
		return new OAuth2RestTemplate(resource, context);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SecurityApiOrderApplication.class);
	}

}
