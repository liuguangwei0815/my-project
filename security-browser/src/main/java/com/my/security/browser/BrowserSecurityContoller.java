package com.my.security.browser;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.my.security.browser.support.SimpleResponse;
import com.my.security.browser.support.SocialUserInfo;
import com.my.security.properites.SecurityProperties;

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
	
	@Autowired
	private SecurityProperties securityProperties;
	@Autowired
	private ProviderSignInUtils providerSignInUtils;
	
	@RequestMapping("/authentication/require")
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public SimpleResponse autenticationRequir(HttpServletRequest request,HttpServletResponse response) throws IOException {
		SavedRequest save = requestCache.getRequest(request, response);
		if(save !=null) {
			//引发安全认证的请求路径
			String targetUrl = save.getRedirectUrl();
			log.info("引发安全认证的请求路径==>{}",targetUrl);
			if(targetUrl.endsWith(".html")) {
				log.info("获取登录页面地址:{}",securityProperties.getBrowser().getLoginpage());
				//跳转到登录页面
				strategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginpage());
			}
		}
		
		return new SimpleResponse("需要引导到登录页面");
	}
	
	

	/**
	 * 获取第三方信息controller
	 * @param request
	 * @return
	 */
	@GetMapping("/social/user")
	public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
		SocialUserInfo userInfo = new SocialUserInfo();
		Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
		userInfo.setProviderId(connection.getKey().getProviderId());
		userInfo.setProviderUserId(connection.getKey().getProviderUserId());
		userInfo.setNickname(connection.getDisplayName());
		userInfo.setHeadimg(connection.getImageUrl());
		return userInfo;
	}

	

}
