package com.my.security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MyUserDetailsService implements UserDetailsService , SocialUserDetailsService {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("表单登录获取用户名：{}", username);
		return builUser(username);
	}

	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
		log.info("qq登录获取用户名：{}", userId);
		return builUser(userId);
	}

	private SocialUserDetails builUser(String userId) {
		return new SocialUser(userId, passwordEncoder.encode("123456"), true, true, true, true,
				AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN,ROLE_USER,admin"));
	}

}
