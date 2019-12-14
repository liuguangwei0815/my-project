/**
 * 
 */
package com.my.security.social.qq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

import com.my.security.properites.QQProperties;
import com.my.security.properites.SecurityProperties;
import com.my.security.social.SocialAutoConfigurerAdapter;
import com.my.security.social.qq.connet.QQFactory;

/**
 * @author Administrator
 *
 */
@Configuration
//这个意思是如果没有配置 那么不会加载此类
@ConditionalOnProperty(prefix = "self.security.socail.qq",name = "app-id" )
public class QQAutoConfig extends SocialAutoConfigurerAdapter{

	@Autowired
	private SecurityProperties securityProperties;
	
	
	@Override
	protected ConnectionFactory<?> createConnectionFactory() {
		QQProperties qq = securityProperties.getSocail().getQq();
		return new QQFactory(qq.getProviderId(), qq.getAppId(), qq.getAppSecret());
	}

	
}
