/**
 * 
 */
package com.my.security.resourceconfig;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

/**
 * @author liuwei
 * 
 */
public interface MyPermissionHandler {
	/**
	 * 
	 * @param request 当前请求
	 * @param authentication 当前用户
	 * @return
	 */
	boolean isHasPermision(HttpServletRequest request, Authentication authentication);
}
