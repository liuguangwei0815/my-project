package com.my.security.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * oauth2认证服务配置
 * 
 * @author liuwei
 *
 */
@Configuration
@EnableAuthorizationServer
public class Oauth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("orderApp")//这个是app 注册  这个是客户端应用
		.secret(passwordEncoder.encode("123456"))
		.scopes("write","read")
		.accessTokenValiditySeconds(7200)
		.authorizedGrantTypes("password","authorization_code","refresh_token")
		.redirectUris("http://example.com")
		.resourceIds("order-server","xxxx")// 意思 orderApp 这个客户端能访问 这个资源id为：order-server  和 xxx
		.and()
		.withClient("orderServer")//这个是订单服务注册	
		.secret(passwordEncoder.encode("123456"))
		.scopes("read")
		.accessTokenValiditySeconds(7200)
		.authorizedGrantTypes("password")
		.resourceIds("order-server"); //意思 orderServer 这个客户端能访问 这个资源id为：order-server 
		;
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager).userDetailsService(userDetailsService);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		//访问token 密钥 需要验证
		security.checkTokenAccess("isAuthenticated()");
	}
	
	
	
	

}
