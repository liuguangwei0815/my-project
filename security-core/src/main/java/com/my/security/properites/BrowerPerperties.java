package com.my.security.properites;

import com.my.security.properites.enums.LoginType;
import com.my.security.vaidata.code.SecurityContant;

import lombok.Data;

@Data
public class BrowerPerperties {

	private String loginpage = "/login-simple.html";

	private LoginType loginType = LoginType.JSON;

	private int cookiesInvalidataTimes = 3600;// 一个小时

	private String signUp = "/simple_singUp.html";

	// session 失效地址
	private String sessionInvalideUrl = SecurityContant.SESSON_INVALID;
	// session 登录个数
	private int maximumSessions = 1;
	// 最大session 之后是覆盖之前的session 还是保存原来的session 默认flase 覆盖旧的session 登录状态
	private boolean maxSessionsPreventsLogin = false;
	//session 退出登录的页面，security 默认是直接跟在登录页面后面加上?logOut ,那么需要改的需要 配置两个地址 1、、一个是退出登录连接 2、一个是配置跳转到退出登录的特定页面
	private String signOutUrl;

}
