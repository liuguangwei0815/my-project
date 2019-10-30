package com.my.security.exception;

import lombok.Getter;

@Getter
public class UserNoExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6493159147562966846L;

	private int id;

	public UserNoExistException(int id) {
		super("user not exits");
		this.id = id;
	}

}
