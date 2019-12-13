package com.my.security.browser;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.my.security.browser.authentication.MyAuthenticationFailHandler;
import com.my.security.browser.authentication.MyAuthenticationSuccessHandler;
import com.my.security.properites.SecurityProperties;
import com.my.security.sms.authentication.SmsAuthenticationConfig;
import com.my.security.vaidata.code.SecurityContant;
import com.my.security.vaidata.code.SmsAndImageValidataFilterConfig;

import lombok.extern.slf4j.Slf4j;

@Configurable
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
@Slf4j
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
	@Autowired
	private MyAuthenticationFailHandler myAuthenticationFailHandler;
	@Autowired
	private SecurityProperties securityProperties;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private DataSource dataSource;
	@Autowired
	private SmsAuthenticationConfig smsAuthenticationConfig;
	@Autowired
	private SmsAndImageValidataFilterConfig smsAndImageValidataFilterConfig;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

//		SmsAndImageValidataFilter codeFilter = new SmsAndImageValidataFilter();
//		codeFilter.afterPropertiesSet();
		
		
		log.info("获取配置文件的登录页面:{}", securityProperties.getBrowser().getLoginpage());
		//短信验证码校验过滤器配置
		http.apply(smsAndImageValidataFilterConfig);
		//手机短信登录授权
		http.apply(smsAuthenticationConfig);
		// 认证方式（用户密码登录）
		http.formLogin()
				// basic 认证
				// http.httpBasic()
				// 需要认证之后的请求访问路径
				.loginPage(SecurityContant.AUTHENTICATION_REQUIRE)
				// 提交登录页认证的设置 action=""
				.loginProcessingUrl(SecurityContant.AUTHENTICATION_FORM)
				//登录成功之后的处理
				.successHandler(myAuthenticationSuccessHandler)
				//登录失败之后的处理
				.failureHandler(myAuthenticationFailHandler)
				.and()
				// 授权
				.authorizeRequests().antMatchers(
						SecurityContant.AUTHENTICATION_REQUIRE,
						SecurityContant.ERROR,
						SecurityContant.MYCODE,
						SecurityContant.AUTHENTICATION_MOBILE,
						securityProperties.getBrowser().getLoginpage()
						).permitAll()
				//.antMatchers(getUrlaArr()).permitAll()
				.anyRequest().authenticated().and().csrf().disable();
		//记住我
		http.rememberMe()
		.tokenRepository(persistentTokenRepository())
		.tokenValiditySeconds(securityProperties.getBrowser().getCookiesInvalidataTimes())
		.userDetailsService(userDetailsService);

		// http.antMatcher("/**").authorizeRequests().anyRequest().permitAll();

	}
	
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
		repo.setDataSource(dataSource);
		//repo.setCreateTableOnStartup(true);
		return repo;
	}


}
