package com.my.security.authorization;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

/**
 * oauth2认证服务配置
 * 
 * @author liuwei
 *
 */
@Configuration
@EnableAuthorizationServer
@EnableJdbcHttpSession
public class Oauth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService userDetailsService;
	// 注入数据源
	@Autowired
	private DataSource dataSource;

	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(dataSource);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(dataSource);// 直接填写数据源就可以
// 直接配置数据源 在表填写数据就可以		
//		clients.inMemory().withClient("orderApp")//这个是app 注册  这个是客户端应用
//		.secret(passwordEncoder.encode("123456"))
//		.scopes("write","read")
//		.accessTokenValiditySeconds(7200)
//		.authorizedGrantTypes("password","authorization_code","refresh_token")
//		.redirectUris("http://example.com")
//		.resourceIds("order-server","xxxx")// 意思 orderApp 这个客户端能访问 这个资源id为：order-server  和 xxx
//		.and()
//		.withClient("orderServer")//这个是订单服务注册	
//		.secret(passwordEncoder.encode("123456"))
//		.scopes("read")
//		.accessTokenValiditySeconds(7200)
//		.authorizedGrantTypes("password")
//		.resourceIds("order-server"); //意思 orderServer 这个客户端能访问 这个资源id为：order-server 
//		;
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager)
		// 如果开启了refresh_token 是没有用户名和密码的，所有要指定这个userDetailsServer
		.userDetailsService(userDetailsService);
		endpoints.tokenStore(tokenStore());
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		// 访问token 需要验证
		security.checkTokenAccess("isAuthenticated()");
	}

}
