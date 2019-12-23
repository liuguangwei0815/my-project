package com.my.security.authorization;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * jwt 配置
 * @author Administrator
 *
 */
@Configuration
public class JwtConfig {
	
	@Bean
	public TokenStore jwtTokenStore() {
		return  new  JwtTokenStore(jwtAccessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter conver = new JwtAccessTokenConverter();
		conver.setSigningKey("mySk");
		return conver;
	}
	

}
