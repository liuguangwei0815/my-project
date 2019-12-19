package com.my.security.vaidata.code.processor;

import org.springframework.web.context.request.ServletWebRequest;

import com.my.security.vaidata.code.ValidataCode;

/**
 * session 存储策略
 * @author Administrator
 *
 */
public interface SessionResposityStrategy {
	
	void save(ServletWebRequest request,ValidataCode code,String codeType);
	
	ValidataCode get(ServletWebRequest request,String codeType);
	
	void remove(ServletWebRequest request,String codeType);
	

}
