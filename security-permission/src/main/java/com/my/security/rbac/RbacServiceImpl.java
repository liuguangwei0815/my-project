package com.my.security.rbac;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;


@Component("rbacService")
public class RbacServiceImpl implements RbacService {
	
	
	private AntPathMatcher mathcer  = new AntPathMatcher();

	@Override
	public boolean isHashPermission(HttpServletRequest request, Authentication authentication) {
		
		boolean isPermission = false;
		
		Object principal = authentication.getPrincipal();
		
		if(principal instanceof UserDetails) {
			
			String username = ((UserDetails)authentication).getUsername();
			//通过用户名查询url
			Set<String> url = new HashSet<>();
			isPermission = url.stream().filter(e->mathcer.match(request.getRequestURI(), e)).findFirst().isPresent();
		}
		return isPermission;
	}

}
