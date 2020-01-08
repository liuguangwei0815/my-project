/**
 * 
 */
package com.my.security.resourceconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.access.ExceptionTranslationFilter;

/**
 * @author liuwei 定位网关为资源服务器
 */
@Configuration
@EnableResourceServer
public class GeteWayResourceConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private MyOAuth2WebSecurityExpressionHandler myOAuth2WebSecurityExpressionHandler;
	@Autowired
	private MyOAuth2AccessDeniedHandler myOAuth2AccessDeniedHandler;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		/**
		 * 配资该资源id
		 */
		resources.resourceId("geteway");
		resources.expressionHandler(myOAuth2WebSecurityExpressionHandler);// 指定表达式处理器，这样子access下的处理去才能生效
		resources.accessDeniedHandler(myOAuth2AccessDeniedHandler);//这个专门处理403的处理去
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// 因为需要通过访问认证服务器获取密钥，所以需要吧token的请求放开
		http.authorizeRequests().antMatchers("/token/**").permitAll()
				// .anyRequest().authenticated();
				.anyRequest().access("#myPermissionHandler.isHasPermision(request,authentication)");// 将所有的链接都进行授权验证
																									// ,request 当前请求
																									// ，Authentication
																									// 当前用户信息
		//审计日志 应该放在认证之后，授权之前
		http.addFilterBefore(new AuditLogFilter(), ExceptionTranslationFilter.class);
	}

}
