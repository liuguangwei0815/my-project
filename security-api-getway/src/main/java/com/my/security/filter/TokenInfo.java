package com.my.security.filter;

import java.util.Date;

import lombok.Data;

@Data
public class TokenInfo {


	private boolean active;
	
	private String client_id;

	private String[] scope;

	private String user_name;

	private String[] aud;// 就是我们配置resourceId的数据

	private Date exp;

	private String[] authorities;


}
