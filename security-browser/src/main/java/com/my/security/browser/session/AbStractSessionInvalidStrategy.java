package com.my.security.browser.session;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

import lombok.extern.slf4j.Slf4j;

/**
 * session 失效或者并发策略 类似模板方法
 * 
 * @author Administrator
 *
 */
@Slf4j
public class AbStractSessionInvalidStrategy {

	// 跳转工具类
	private RedirectStrategy stategy = new DefaultRedirectStrategy();

	// 目标跳转方法
	private String destinationUrl;
	// 是否并发引起的ssesion失效
	private boolean isConncurrent = Boolean.FALSE;

	public AbStractSessionInvalidStrategy(String destinationUrl) {
		this.destinationUrl = destinationUrl;
	}

	/**
	 * session 失效处理
	 * 
	 * @throws IOException
	 */
	protected void onInvalideSessionStrategy(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		request.getSession();
		
		// 获取源Url
		String sourceUrl = request.getRequestURI();
		if (StringUtils.endsWithIgnoreCase(sourceUrl, ".html")) {
			String targetUrl = destinationUrl+".html";
			log.info("session失效,跳转到"+targetUrl);
			stategy.sendRedirect(request, response, targetUrl);
		} else {
			String message ="session 失效";
			if(isConncurrent) {
				message = message+",可能ssesion 并发造成的";
			}
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write("session 失效");

		}
	}

	public void setConncurrent(boolean isConncurrent) {
		this.isConncurrent = isConncurrent;
	}

}
