package com.my.security.securityconfig;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * 退出成功之后处理
 * @author liuwei
 *
 */
@Component
public class MyLogOutSuccessHandler implements LogoutSuccessHandler{

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		String redirect_uri = request.getParameter("redirect_uri");
		if(StringUtils.isNotBlank(redirect_uri)) {
			response.sendRedirect(redirect_uri);
		}
	}

}
