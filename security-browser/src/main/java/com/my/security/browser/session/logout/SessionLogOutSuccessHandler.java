package com.my.security.browser.session.logout;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.security.support.SimpleResponse;

public class SessionLogOutSuccessHandler implements LogoutSuccessHandler{
	
	
	private ObjectMapper mapper = new ObjectMapper();
	RedirectStrategy stegy = new  DefaultRedirectStrategy();
	
	private String logOutSuccessUrl;
	public SessionLogOutSuccessHandler(String logOutSuccessUrl) {
		this.logOutSuccessUrl = logOutSuccessUrl;
	}
	
	

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		if(StringUtils.isBlank(logOutSuccessUrl)) {
			response.setStatus(HttpStatus.OK.value());
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(mapper.writeValueAsString(new SimpleResponse("退出成功!")));
		}else {
			stegy.sendRedirect(request, response, logOutSuccessUrl);
		}
			
		
	}

}
