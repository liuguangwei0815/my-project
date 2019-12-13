/**
 * 
 */
package com.my.security.vaidata.code.processor;

import java.io.IOException;

import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author liuwei
 *
 */
public interface ValidataProcessor {
	
	String SESSION_KEY_FIX = "SESSION_KEY_FOR_CODE_";

	/**
	 *  验证码建立和发送
	 * @param request
	 * @throws ServletRequestBindingException 
	 * @throws IOException 
	 */
	public void create(ServletWebRequest request) throws ServletRequestBindingException, IOException;

	void validataSmsCode(ServletWebRequest request) throws ServletRequestBindingException;

}
