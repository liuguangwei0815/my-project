package com.my.security.browser.session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.session.InvalidSessionStrategy;

/**
 * session 过期失效策略
 * @author Administrator
 *
 */
public class SessionExprireStategy extends AbStractSessionInvalidStrategy implements InvalidSessionStrategy{

	public SessionExprireStategy(String destinationUrl) {
		super(destinationUrl);
	}

	@Override
	public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		onInvalideSessionStrategy(request, response);
	}

}
