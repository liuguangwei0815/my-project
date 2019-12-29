package com.my.security.resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * 定义api-order 为资源服务器，那么首先第一步首选得让应用他自己知道自己是什么资源服务 资源id是什么
 * 
 * @author liuwei
 *
 */
@Configuration
@EnableResourceServer // 有该标识标识他是oauth2中的资源服务角色
public class ResourceConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		// 首先要让该应用知道自己是什么资源服务器
		resources.resourceId("order-server");// 这个需要和你认证服务的resourceId匹配
	}

	// 这个也是想关于授权的控制，可以覆盖
	@Override
	public void configure(HttpSecurity http) throws Exception {
		// 举个例子 除了/haha 不用验证 其他的都需要认证
//		http.authorizeRequests().antMatchers("/hahaa").permitAll()
//		.anyRequest().authenticated();
		//控制如果只有令牌范围有read 才能read 有write 才能 wirte
		http.authorizeRequests()
		//分开post 和 get 请求  
		.antMatchers(HttpMethod.POST).access("#oauth2.hasScope('write')")//令牌的cope 有write 才能访问post 请求
		.antMatchers(HttpMethod.GET).access("#oauth2.hasScope('read')");//令牌的cope 有read 才能访问GET 请求
		
		
	}

}
