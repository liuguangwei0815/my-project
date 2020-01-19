/*
 * package com.my.security.config;
 * 
 * import org.springframework.boot.autoconfigure.security.servlet.
 * UserDetailsServiceAutoConfiguration; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.security.core.authority.AuthorityUtils; import
 * org.springframework.security.core.userdetails.User; import
 * org.springframework.security.core.userdetails.UserDetails; import
 * org.springframework.security.core.userdetails.UserDetailsService; import
 * org.springframework.security.core.userdetails.UsernameNotFoundException;
 * import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 * import org.springframework.security.crypto.password.PasswordEncoder; import
 * org.springframework.stereotype.Component;
 * 
 *//**
	 * 
	 * @author liuwei
	 *
	 *//*
		 * @Component public class MyUserDetailService implements UserDetailsService {
		 * 
		 * @Bean public PasswordEncoder passwordEncoder() { return new
		 * BCryptPasswordEncoder(); }
		 * 
		 * @Override public UserDetails loadUserByUsername(String username) throws
		 * UsernameNotFoundException { return new User(username,
		 * passwordEncoder().encode("123456"), true, true, true, true,
		 * AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN")); }
		 * 
		 * }
		 */