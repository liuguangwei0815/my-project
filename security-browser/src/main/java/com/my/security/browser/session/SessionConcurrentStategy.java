package com.my.security.browser.session;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * session 并发失效策略
 * @author Administrator
 *
 */
public class SessionConcurrentStategy extends AbStractSessionInvalidStrategy implements SessionInformationExpiredStrategy{

	public SessionConcurrentStategy(String destinationUrl) {
		super(destinationUrl);
	}

	@Override
	public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
		onInvalideSessionStrategy(event.getRequest(), event.getResponse());		
	}
	
	@Override
	public void setConncurrent(boolean isConncurrent) {
		super.setConncurrent(Boolean.TRUE);
	}

}
