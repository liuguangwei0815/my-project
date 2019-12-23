/**
 * 
 */
package com.my.security.authrority;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 *
 */
@Component
public class AuthorizationProviderManagerImple implements AuthorizationProviderManager {

	@Autowired
	private List<AuthorizationProvider> authorizationProviders;

	@Override
	public void config(
			ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizationconfig) {
		authorizationProviders.stream().forEach(e -> e.config(authorizationconfig));
		//authorizationconfig.anyRequest().authenticated();
	}

}
