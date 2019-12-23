/**
 * 
 */
package com.my.security.authrority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import com.my.security.properites.SecurityProperties;
import com.my.security.vaidata.code.SecurityContant;

/**
 * @author Administrator
 *
 */
@Component
public class AuthorizationProviderImple implements AuthorizationProvider {

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public void config(
			ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizationconfig) {
		authorizationconfig.antMatchers(SecurityContant.AUTHENTICATION_REQUIRE, SecurityContant.ERROR,
				SecurityContant.MYCODE, SecurityContant.AUTHENTICATION_MOBILE, SecurityContant.USER_REGIST,
				securityProperties.getBrowser().getLoginpage(), securityProperties.getBrowser().getSignUp(),
				securityProperties.getBrowser().getSessionInvalideUrl() + ".html",
				securityProperties.getBrowser().getSessionInvalideUrl() + ".json", SecurityContant.DEMO_SIGNOUT,
				SecurityContant.DEMO_SIGNOUT).permitAll();
	}

}
