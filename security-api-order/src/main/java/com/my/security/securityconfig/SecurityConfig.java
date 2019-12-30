//package com.my.security.securityconfig;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
//import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
//import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
//import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
//
///**
// * 第二 需要让资源服务器去验证token 第三 是要让验证成功之后可以获取用户的信息
// * 
// * @author liuwei
// *//  因为启动了zuul 在zuul 进行了安全相关的配置 所以这里直接注释掉
// */
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//	
//	@Autowired
//	private UserDetailsService userDetailsService;
//
//	/**
//	 * 第一资源服务 检token的远程token 服务
//	 * 
//	 * @return
//	 */
//	@Bean
//	public ResourceServerTokenServices resourceServerTokenServices() {
//
//		RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
//		remoteTokenServices.setClientId("orderServer");
//		remoteTokenServices.setClientSecret("123456");
//		remoteTokenServices.setCheckTokenEndpointUrl("http://localhost:7024/oauth/check_token");
//		// check token 之后会将用户信息返回个资源服务 ，所以在这里做转换
//		remoteTokenServices.setAccessTokenConverter(getAccessTokenConveter());
//		return remoteTokenServices;
//	}
//
//	private AccessTokenConverter getAccessTokenConveter() {
//		DefaultAccessTokenConverter conver = new DefaultAccessTokenConverter();
//		DefaultUserAuthenticationConverter duc = new DefaultUserAuthenticationConverter();
//		duc.setUserDetailsService(userDetailsService);
//		conver.setUserTokenConverter(duc);
//		return conver;
//	}
//
////	/**
////	 * AuthenticationManager 去管理管理用户信息的
////	 * 获取用户信息
////	 */
////	@Bean
////	public AuthenticationManager authenticationManager() {
////		OAuth2AuthenticationManager manager = new OAuth2AuthenticationManager();
////		manager.setTokenServices(resourceServerTokenServices());
////		return manager;
////	}
//
//	/**
//	 * 这种写法和上面的写法一样
//	 */
//	@Bean
//	@Override
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//		OAuth2AuthenticationManager manager = new OAuth2AuthenticationManager();
//		manager.setTokenServices(resourceServerTokenServices());
//		return manager;
//	}
//
//}
