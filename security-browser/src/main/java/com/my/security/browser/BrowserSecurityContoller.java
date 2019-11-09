package com.my.security.browser;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.my.security.browser.support.SimpleResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 用作登录请求需要验证的控制判断逻辑
 * @author Administrator
 *
 */
@RestController
@Slf4j
public class BrowserSecurityContoller {
	
	private RequestCache requestCache = new HttpSessionRequestCache();
	
	private RedirectStrategy strategy = new DefaultRedirectStrategy();
	
	@RequestMapping("/authentication/require")
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public SimpleResponse autenticationRequir(HttpServletRequest request,HttpServletResponse response) throws IOException {
		SavedRequest save = requestCache.getRequest(request, response);
		if(save !=null) {
			//引发安全认证的请求路径
			String targetUrl = save.getRedirectUrl();
			log.info("引发安全认证的请求路径==>{}",targetUrl);
			if(targetUrl.endsWith(".html")) {
				//跳转到登录页面
				strategy.sendRedirect(request, response, "");
			}
		}
		
		return new SimpleResponse("需要引导到登录页面");
	}

}
