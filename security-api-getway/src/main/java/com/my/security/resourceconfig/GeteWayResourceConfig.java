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
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

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
	@Autowired
	private MyOAuth2AuthenticationEntryPoint myOAuth2AuthenticationEntryPoint;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		/**
		 * 配资该资源id
		 */
		resources.resourceId("geteway");
		resources.expressionHandler(myOAuth2WebSecurityExpressionHandler);// 指定表达式处理器，这样子access下的处理去才能生效
		resources.accessDeniedHandler(myOAuth2AccessDeniedHandler);//这个专门处理403的处理去
		resources.authenticationEntryPoint(myOAuth2AuthenticationEntryPoint);//处理401 有三种
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
		//限流过滤器 放在spring 过滤器第一个
		http.addFilterBefore(new MyLimitRateFilter(), SecurityContextPersistenceFilter.class);
		//审计日志 应该放在认证之后，授权之前  如果没有异常 那么 就应该在Oath2ClientAuthentictionProccessingFilter 之后，但是如果发生了异常，那么还会经过
		// 给拦截了拦截了，然后直接返抛出异常了 通过FilterSecuritInterctipin 抛出给ExceptionTraslationFilter 通过对应的异常 提交各对应的异常处理类，如401 403
		http.addFilterBefore(new AuditLogFilter(), ExceptionTranslationFilter.class);
	}

}
