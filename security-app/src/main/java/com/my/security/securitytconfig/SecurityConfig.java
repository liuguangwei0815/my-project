package com.my.security.securitytconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.social.security.SpringSocialConfigurer;

import com.my.security.authentication.MyAuthenticationFailHandler;
import com.my.security.authentication.MyAuthenticationSuccessHandler;
import com.my.security.authrority.AuthorizationProviderManager;
import com.my.security.properites.SecurityProperties;
import com.my.security.sms.authentication.SmsAuthenticationConfig;
import com.my.security.vaidata.code.SecurityContant;
import com.my.security.vaidata.code.SmsAndImageValidataFilterConfig;

import lombok.extern.slf4j.Slf4j;

@Configurable
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;
	@Autowired
	private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
	@Autowired
	private MyAuthenticationFailHandler myAuthenticationFailHandler;
	@Autowired
	private SmsAuthenticationConfig smsAuthenticationConfig;
	@Autowired
	private SmsAndImageValidataFilterConfig smsAndImageValidataFilterConfig;
	@Autowired
	private SpringSocialConfigurer springSocialConfigurer;
	@Autowired
	private AuthorizationProviderManager authorizationProviderManager;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("获取配置文件的登录页面:{}", securityProperties.getBrowser().getLoginpage());
		// 短信验证码校验过滤器配置
		http.apply(smsAndImageValidataFilterConfig);
		// 手机短信登录授权
		http.apply(smsAuthenticationConfig);
		// QQ登录
		http.apply(springSocialConfigurer);
		// 认证方式（用户密码登录）
		http.formLogin()
				// basic 认证
				// http.httpBasic()
				// 需要认证之后的请求访问路径
//				.loginPage(SecurityContant.AUTHENTICATION_REQUIRE)
//				// 提交登录页认证的设置 action=""
//				.loginProcessingUrl(SecurityContant.AUTHENTICATION_FORM)
				// 登录成功之后的处理
//				.successHandler(myAuthenticationSuccessHandler)
//				// 登录失败之后的处理
//				.failureHandler(myAuthenticationFailHandler).and()
				// 授权
//				.and()
//				.authorizeRequests()
//				.antMatchers(SecurityContant.AUTHENTICATION_REQUIRE, SecurityContant.ERROR, SecurityContant.MYCODE,
//						SecurityContant.AUTHENTICATION_MOBILE, SecurityContant.USER_REGIST,
//						securityProperties.getBrowser().getLoginpage(), securityProperties.getBrowser().getSignUp(),
//						securityProperties.getBrowser().getSessionInvalideUrl() + ".html",
//						securityProperties.getBrowser().getSessionInvalideUrl() + ".json", SecurityContant.DEMO_SIGNOUT)
//				.permitAll()
//				// .antMatchers(getUrlaArr()).permitAll()
//				.anyRequest().authenticated()
				.and().csrf().disable();
		
		authorizationProviderManager.config(http.authorizeRequests());
	}

	/**
	 * 一定得这样子注入 不然找不到AuthenticationManager
	 */
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
}
