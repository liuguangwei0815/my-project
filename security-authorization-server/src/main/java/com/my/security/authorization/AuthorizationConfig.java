package com.my.security.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * 授权配置
 * 
 * @author Administrator
 *
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	private TokenStore tokenStore;
	@Autowired
	private JwtAccessTokenConverter jwtAccessTokenConverter;
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		//这句话设置的意思 ， 因为应用client 获取到了jwt 需要再去服务器获取秘钥 ，所以访问秘钥需要授权
		security.tokenKeyAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		//设置两个授权服务端
		clients.inMemory().withClient("ssoClinet1").secret(passwordEncoder().encode("ssoClinetpw"))
				.authorizedGrantTypes("authorization_code", "refresh_token").scopes("all")
				.redirectUris("http://localhost:7020/login").autoApprove(true)
				.and()
				.withClient("ssoClinet2").secret(passwordEncoder().encode("ssoClinetpw2"))
				.authorizedGrantTypes("authorization_code", "refresh_token").scopes("all")
				.redirectUris("http://localhost:7021/login");
				;
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore);
		endpoints.accessTokenConverter(jwtAccessTokenConverter);
	}

}
