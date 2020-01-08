/**
 * 
 */
package com.my.security.resourceconfig;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuwei
 *  专门处理认证异常的的处理器，这里可以控制权限返回的异常
 */
@Component
@Slf4j
public class MyOAuth2AccessDeniedHandler extends OAuth2AccessDeniedHandler {

	/**
	 * authException 异常的原因
	 */
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException)
			throws IOException, ServletException {
		log.info("up log fail 403");
		request.setAttribute("hasuplogstatus", "yes");//这里做标识，为后面的审计日志流程坐标
		super.handle(request, response, authException);
	}
	
}
