package com.my.security.properites;

import lombok.Data;

@Data
public class QQProperties{
	
	/**
	 * Application id.
	 */
	private String appId;

	/**
	 * Application secret.
	 */
	private String appSecret;
	
	//提供商标识
	private String providerId = "callback.do";
	
	//social 默认拦截地址
	private String filterProcessesUrl = "/auth";

}
