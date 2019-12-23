package com.my.security.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import com.my.security.authrority.AuthorizationProvider;

/**
 * 将之前只能配置到browser 包的 权限配置给挪出来 可以增加方便的的给每个链接给权限
 * @author Administrator
 *
 */
@Component
public class DemoAuthorizationProvider implements AuthorizationProvider{

	@Override
	public void config(
			ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizationconfig) {
		authorizationconfig.antMatchers("/user/*").hasAuthority("query");
	}

}
