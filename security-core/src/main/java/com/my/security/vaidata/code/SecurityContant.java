package com.my.security.vaidata.code;

public class SecurityContant {

	public static String SESSION_KEY_FIX = "SESSION_KEY_FOR_CODE_";

	public static String SESSION_KEY_SMS_KEY = "SESSION_KEY_SMS_KEY";

	public static String MOBILE = "mobile";// 页面手机号的name名称

	/**
	 * 需要认证之后的请求访问路径
	 */
	public static String AUTHENTICATION_REQUIRE = "/authentication/require";
	
	/**
	 * 手机短信验证码认证地址form 提交地址
	 */
	public static String AUTHENTICATION_MOBILE = "/authentication/mobile";

	/**
	 * 认证提交方法
	 */
	public static String AUTHENTICATION_FORM = "/authentication/form";

	/**
	 * 错误页面跳转
	 */
	public static String ERROR = "/error";
	/**
	 * 验证码链接正则
	 */
	public static String MYCODE = "/mycode/*";


	/**
	 * 图形验证码认证地址form 获取图形验证码Name参数
	 */
	public static String IMAGECODE = "imageCode";
	
	/**
	 * 手机短信验证码认证地址form 获取短信验证码Name参数
	 */
	public static String SMSCODE = "smsCode";
	
	
	/**
	 * 注册
	 */
	public static String USER_REGIST = "/user/regist";
	
	/**
	 * session失效连接
	 */
	public static String SESSON_INVALID = "/sessioninvalid";
	
	
	
}
