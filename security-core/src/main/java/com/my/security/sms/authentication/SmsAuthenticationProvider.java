package com.my.security.sms.authentication;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import lombok.Getter;
import lombok.Setter;
/**
 * 短信认证提供者
 * @author liuwei
 *
 */
public class SmsAuthenticationProvider implements AuthenticationProvider {

	@Getter
	@Setter
	private UserDetailsService userDetailsService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		SmsAuthenticationToken token = (SmsAuthenticationToken)authentication;
		UserDetails user = userDetailsService.loadUserByUsername((String) token.getPrincipal());
		if(user==null) {
			throw new InternalAuthenticationServiceException(
					"sms-UserDetailsService returned null, which is an interface contract violation");
		}
		//组装新的Token 已认证的token
		SmsAuthenticationToken resultAuthen = new SmsAuthenticationToken(user, user.getAuthorities());
		resultAuthen.setDetails(token.getDetails());
		return resultAuthen;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return SmsAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
