package com.my.security.browser.code.reposity;

import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.my.security.vaidata.code.ValidataCode;
import com.my.security.vaidata.code.processor.SessionResposityStrategy;

/**
 * 浏览器保存session 
 * @author Administrator
 *
 */
@Component
public class BorrowerSessionResposityStrategy implements SessionResposityStrategy {
	
	private SessionStrategy strategy = new  HttpSessionSessionStrategy(); 
	
	String SESSION_KEY_FIX = "SESSION_KEY_FOR_CODE_";

	@Override
	public void save(ServletWebRequest request, ValidataCode code, String type) {
		strategy.setAttribute(request, bullKey(type), code);
	}

	private String bullKey(String type) {
		return SESSION_KEY_FIX+type.toUpperCase();
	}

	@Override
	public ValidataCode get(ServletWebRequest request, String type) {
		return (ValidataCode) strategy.getAttribute(request, bullKey(type));
	}

	@Override
	public void remove(ServletWebRequest request, String type) {
		strategy.removeAttribute(request,  bullKey(type));
	}

}
