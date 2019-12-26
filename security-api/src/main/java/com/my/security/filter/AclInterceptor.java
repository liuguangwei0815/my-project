/**
 * 
 */
package com.my.security.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.my.security.user.entity.User;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuwei 简单的授权方式acl access control list
 */
@Component
@Slf4j
public class AclInterceptor extends HandlerInterceptorAdapter {

	private static String[] str = new String[] { "/user/login" };

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean result = Boolean.TRUE;

		Object user = request.getSession().getAttribute("user");
		
		if(!ArrayUtils.contains(str, request.getRequestURI())) {
			if (user == null) {
				response.setContentType("text/plain");
				response.getWriter().write("need autentication ");
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				response.flushBuffer();
				result = Boolean.FALSE;
			} else {
				try {
					if (!((User) user).permission(request.getMethod())) {
						response.setContentType("text/plain");
						response.getWriter().write("forbidden");
						response.setStatus(HttpStatus.FORBIDDEN.value());
						response.flushBuffer();
						result = Boolean.FALSE;
					}
				} catch (Exception e) {
					log.error("判断权限异常:{}", e);
				}
			}
		}
		return result;

	}

}
