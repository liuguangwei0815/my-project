package com.my.security.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.security.properites.SecurityProperties;
import com.my.security.properites.enums.LoginType;

import lombok.extern.slf4j.Slf4j;

@Component("myAuthenticationSuccessHandler")
@Slf4j
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		log.info("登录成功 ! authenticaiton info : {}", mapper.writeValueAsString(authentication));
		log.info("登录类型：{}", securityProperties.getBrowser().getLoginType());
		if (securityProperties.getBrowser().getLoginType().equals(LoginType.JSON)) {
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(mapper.writeValueAsString(authentication));
		} else {
			super.onAuthenticationSuccess(request, response, authentication);
		}
	}

}
