package com.my.security.social;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.web.context.request.ServletWebRequest;

import com.my.security.properites.SecurityProperties;

@Configuration
@EnableSocial
public class SocailConfig extends SocialConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	@Autowired
	private SecurityProperties securityProperties;
	//也可以当做参数注进来
//	@Autowired
//	private ConnectionFactoryLocator connectionFactoryLocator;

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		JdbcUsersConnectionRepository jcr = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator,
				Encryptors.noOpText());
		jcr.setTablePrefix("socail_");
		return jcr;
	}

	// 将social 和 securit 配置起来
	@Bean
	public SpringSocialConfigurer springSocialConfigurer() {
		MySpringSocailConfig myconfig = new MySpringSocailConfig(
				securityProperties.getSocail().getQq().getFilterProcessesUrl());
		//social 默认需要实现singnupURl的处理逻辑 需要 注册一个新的用户 或者绑定一个用户 
		myconfig.signupUrl(securityProperties.getBrowser().getSignUp());
		return myconfig;
	}

	@Override
	public UserIdSource getUserIdSource() {
		return new AuthenticationNameUserIdSource();
	}
	
	//这个工具类可以和获取第三方登录的信息 比如图片 qq号码 
	@Bean
	public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
		return new ProviderSignInUtils(connectionFactoryLocator, getUsersConnectionRepository(connectionFactoryLocator));
	}
	
}
