package com.my.security.rbac;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

/**
 * rbac访问接口
 * @author Administrator
 *
 */
public interface RbacService {
	
	public boolean isHashPermission(HttpServletRequest request,Authentication authentication);

}
