package com.my.security.properites;

import lombok.Data;

/**
 * 服务商服务端配置
 * 
 * @author Administrator
 *
 */
@Data
public class ClientInfoProperties {

	private String clientId;// 服务商id
	private String clientSecert;// 服务商密码
	private int accessTokenValiditySeconds;// 这个需要默认值 如果那么这个会造成永久有效
	private String redirectUris;// 默认接收验证码地址

}
