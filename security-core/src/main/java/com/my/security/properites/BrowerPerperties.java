package com.my.security.properites;

import com.my.security.properites.enums.LoginType;

import lombok.Data;

@Data
public class BrowerPerperties {

	private String loginpage = "/login-simple.html";
	
	private LoginType loginType = LoginType.JSON;
	
	private int cookiesInvalidataTimes = 3600;//一个小时

}
