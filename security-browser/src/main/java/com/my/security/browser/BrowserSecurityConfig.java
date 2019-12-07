package com.my.security.browser;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.my.security.browser.authentication.MyAuthenticationFailHandler;
import com.my.security.browser.authentication.MyAuthenticationSuccessHandler;
import com.my.security.properites.SecurityProperties;
import com.my.security.vaidata.code.ImageCodeFilter;

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
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		ImageCodeFilter imageCodeFilter = new ImageCodeFilter();
		imageCodeFilter.setAuthenticationFailureHandler(myAuthenticationFailHandler);
		imageCodeFilter.setSecurityProperties(securityProperties);
		imageCodeFilter.afterPropertiesSet();
		
		log.info("获取配置文件的登录页面:{}", securityProperties.getBrowser().getLoginpage());
		// 认证方式
		http.addFilterBefore(imageCodeFilter, UsernamePasswordAuthenticationFilter.class);
		http.formLogin()
				// basic 认证
				// http.httpBasic()
				// 错误之后的请求访问路径
				.loginPage("/authentication/require")
				// 提交登录页认证的设置 action=""
				.loginProcessingUrl("/authentication/form")
				//登录成功之后的处理
				.successHandler(myAuthenticationSuccessHandler)
				//登录失败之后的处理
				.failureHandler(myAuthenticationFailHandler)
				.and()
				// 授权
				.authorizeRequests().antMatchers("/authentication/require").permitAll().antMatchers(getUrlaArr())
				.permitAll().anyRequest().authenticated().and().csrf().disable();
		//记住我
		http.rememberMe()
		.tokenRepository(persistentTokenRepository())
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
	

	// 获取配置文件的登录连接页面
	private String[] getUrlaArr() {
		List<String> loginUrl = new ArrayList<String>();
		loginUrl.add("/login-simple.html");
		loginUrl.add("/error");
		loginUrl.add("/image/code");
		if (securityProperties.getBrowser() != null
				&& StringUtils.isNotBlank(securityProperties.getBrowser().getLoginpage())
				&& !StringUtils.equals("/login-simple.html", securityProperties.getBrowser().getLoginpage())) {
			loginUrl.add(securityProperties.getBrowser().getLoginpage());
		}
		String[] urlArr = new String[loginUrl.size()];
		return loginUrl.toArray(urlArr);
	}

}
