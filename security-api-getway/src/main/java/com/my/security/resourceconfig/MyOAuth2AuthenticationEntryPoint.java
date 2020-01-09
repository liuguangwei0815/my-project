/**
 * 
 */
package com.my.security.resourceconfig;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.http.AccessTokenRequiredException;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuwei
 * 专门处理401错误的
 */
@Slf4j
@Component
public class MyOAuth2AuthenticationEntryPoint extends OAuth2AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		if(authException instanceof AccessTokenRequiredException) {
			log.info("2 更新 log 401");
		}else {
			log.info("2  add log 401");
		}
		
		request.setAttribute("hasuplogstatus", "yes");//这里做标识，为后面的审计日志流程坐标
		
		super.commence(request, response, authException);
	}
	
}
