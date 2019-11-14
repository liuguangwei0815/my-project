package com.my.security.browser.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.security.properites.SecurityProperties;
import com.my.security.properites.enums.LoginType;

import lombok.extern.slf4j.Slf4j;

@Component("myAuthenticationFailHandler")
@Slf4j
public class MyAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {
	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		log.info("登录失败 ! AuthenticationException info : {}", mapper.writeValueAsString(exception));
		log.info("登录类型：{}", securityProperties.getBrowser().getLoginType());
		if (securityProperties.getBrowser().getLoginType().equals(LoginType.JSON)) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(mapper.writeValueAsString(exception));
		} else {
			super.onAuthenticationFailure(request, response, exception);
		}
	}

}
