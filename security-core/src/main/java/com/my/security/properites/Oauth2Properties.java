package com.my.security.properites;

import lombok.Data;

@Data
public class Oauth2Properties {
	
	/**
	 *签名
	 */
	private String  signingKey = "myJwtSnKey";

	ClientInfoProperties[] clients = {};

}
