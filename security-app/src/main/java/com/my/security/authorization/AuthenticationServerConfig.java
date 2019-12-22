package com.my.security.authorization;

import java.util.Arrays;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.my.security.properites.ClientInfoProperties;
import com.my.security.properites.SecurityProperties;

import lombok.extern.slf4j.Slf4j;

/**
 * 认证服务器配置
 * 
 * @author Administrator
 *
 */
@Configuration
@EnableAuthorizationServer
@Slf4j
public class AuthenticationServerConfig extends AuthorizationServerConfigurerAdapter {
//
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private SecurityProperties securityProperties;
	@Autowired
	private TokenStore tokenStore;
	@Autowired(required = false)
	private JwtAccessTokenConverter jwtAccessTokenConverter;

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

		// 配置token存储方式
		endpoints.tokenStore(tokenStore);
		// 密码认证模式必须配置一下语句
		endpoints.authenticationManager(authenticationManager).userDetailsService(userDetailsService);
		if (jwtAccessTokenConverter != null)
			endpoints.accessTokenConverter(jwtAccessTokenConverter);
	}

//
//	@Override
//	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//		// 设置服务认证的加密方式
//		//加了下面两句话，客户端才能通过jwt 获取 token_key
//		security.allowFormAuthenticationForClients();
//		security.tokenKeyAccess("isAuthenticated()");
//	}
//
//	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		InMemoryClientDetailsServiceBuilder builder = clients.inMemory(); // 使用in-memory存储
		if (ArrayUtils.isNotEmpty(securityProperties.getOauth2().getClients())) {
			Arrays.stream(securityProperties.getOauth2().getClients()).forEach(e -> buildMerory(e, builder));
		} else {
			log.error("outh2 get client info Nothing");

		}
	}

	private void buildMerory(ClientInfoProperties e, InMemoryClientDetailsServiceBuilder builder) {
		builder.withClient(e.getClientId()) // client_id
				.secret(passwordEncoder.encode(e.getClientSecert())) // client_secret
				.authorizedGrantTypes("authorization_code", "password", "refresh_token") // 该client允许的授权类型
				.accessTokenValiditySeconds(e.getAccessTokenValiditySeconds()).redirectUris(e.getRedirectUris())
				.scopes("all"); // 允许的授权范围
	}

}
