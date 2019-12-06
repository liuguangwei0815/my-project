package com.my.security.vaidata.code;

import org.springframework.security.core.AuthenticationException;

public class ImageCodeException extends AuthenticationException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6284695020317251111L;

	public ImageCodeException(String msg) {
		super(msg);
	}

}
