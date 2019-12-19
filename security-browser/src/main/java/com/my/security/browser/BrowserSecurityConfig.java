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
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import com.my.security.browser.authentication.MyAuthenticationFailHandler;
import com.my.security.browser.authentication.MyAuthenticationSuccessHandler;
import com.my.security.browser.session.logout.SessionLogOutSuccessHandler;
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
    @Autowired
    private SpringSocialConfigurer springSocialConfigurer;
    @Autowired
    private InvalidSessionStrategy sessionExprireStategy;
    @Autowired
    private SessionInformationExpiredStrategy sessionConcurrentStategy;
    @Autowired
    private SessionLogOutSuccessHandler sessionLogOutSuccessHandler;
    
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

//		SmsAndImageValidataFilter codeFilter = new SmsAndImageValidataFilter();
//		codeFilter.afterPropertiesSet();
		
		
		log.info("获取配置文件的登录页面:{}", securityProperties.getBrowser().getLoginpage());
		//短信验证码校验过滤器配置
		http.apply(smsAndImageValidataFilterConfig);
		//手机短信登录授权
		http.apply(smsAuthenticationConfig);
		//QQ登录
		http.apply(springSocialConfigurer);
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
				//配置退出登录的连接
				.logout().logoutUrl("/signOut")
				//退出登录的处理器，配置是json还是直接跳到一个页面
				.logoutSuccessHandler(sessionLogOutSuccessHandler)
				.deleteCookies("JSESSIONID")
				.and()
				// 授权
				.authorizeRequests().antMatchers(
						SecurityContant.AUTHENTICATION_REQUIRE,
						SecurityContant.ERROR,
						SecurityContant.MYCODE,
						SecurityContant.AUTHENTICATION_MOBILE,
						SecurityContant.USER_REGIST,
						securityProperties.getBrowser().getLoginpage(),
						securityProperties.getBrowser().getSignUp(),
						securityProperties.getBrowser().getSessionInvalideUrl()+".html",
						securityProperties.getBrowser().getSessionInvalideUrl()+".json",
						SecurityContant.DEMO_SIGNOUT
						).permitAll()
				//.antMatchers(getUrlaArr()).permitAll()
				.anyRequest().authenticated().and().csrf().disable();
		//记住我
		http.rememberMe()
		.tokenRepository(persistentTokenRepository())
		.tokenValiditySeconds(securityProperties.getBrowser().getCookiesInvalidataTimes())
		.userDetailsService(userDetailsService);
		//session失效处理跳转连接
		http.sessionManagement().invalidSessionStrategy(sessionExprireStategy)
		//sessin并发处理
		.maximumSessions(securityProperties.getBrowser().getMaximumSessions())
		//是否覆盖之旧的登录状态 如果达到了最大的session登录次数
		.maxSessionsPreventsLogin(securityProperties.getBrowser().isMaxSessionsPreventsLogin())
		//并发处理后的处理器
		.expiredSessionStrategy(sessionConcurrentStategy);
		// http.antMatcher("/**").authorizeRequests().anyRequest().permitAll();

	}
	
	/**
	 * 这是记住我的持久化的配置
	 * @return
	 */
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
		repo.setDataSource(dataSource);
		//repo.setCreateTableOnStartup(true);
		return repo;
	}


}
