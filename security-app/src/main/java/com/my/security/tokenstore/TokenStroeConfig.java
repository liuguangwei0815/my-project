package com.my.security.tokenstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.my.security.properites.SecurityProperties;

@Configuration
public class TokenStroeConfig {
	
	@Autowired
	private RedisConnectionFactory redisConnectionFactory;
	
	/**
	 * prefix 是最后一个点 前面的全部去 ， name 就是点后的全部 ，havaingValue 值 ， 如果配置了这个值才会生效
	 * @return
	 */
	@Bean
	@ConditionalOnProperty(prefix = "self.security.oath2.token",name = "storeType",havingValue = "redis")
	public TokenStore tokenStore() {
		return new MyRedisTokenStore(redisConnectionFactory);
	}
	
	@Configuration
	/**
	 * prefix 是最后一个点 前面的全部去 ， name 就是点后的全部 ，havaingValue 值 ，matchIfMissing 为true  如果没有配置了这个值也会生效
	 * @author Administrator
	 *
	 */
	@ConditionalOnProperty(prefix = "self.security.oath2.token",name = "storeType",havingValue = "jwt",matchIfMissing = true)
	public static class JwtStoreConfig{
		
		@Autowired
		private  SecurityProperties securityProperties;
	
		@Bean
		public TokenStore jwtTokenStore() {
			return new JwtTokenStore(jwtAccessTokenConverter());
		}
		
		/**
		 * tokenStore 负责存储 不负载生产token jwtAccestTokenConverter 负责生产jwt
		 * @return
		 */
		@Bean
		public JwtAccessTokenConverter jwtAccessTokenConverter() {
			JwtAccessTokenConverter jwtConver = new JwtAccessTokenConverter();
			jwtConver.setSigningKey(securityProperties.getOauth2().getSigningKey());
			return new JwtAccessTokenConverter();
		}
		
		
	}
	

}
