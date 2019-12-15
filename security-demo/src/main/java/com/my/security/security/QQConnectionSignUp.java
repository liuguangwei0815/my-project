/**
 * 
 */
package com.my.security.security;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * 如果不跳转注册主页绑定页面  这个有客户自己实现  直接数据库默认协议一条用户记录
 */
@Component
public class QQConnectionSignUp implements ConnectionSignUp{

	@Override
	public String execute(Connection<?> connection) {
		//通过社交信息偶人创建用户并返回用户唯一标识，这个需要你做luoji chuli 
		return connection.getDisplayName();
	}

}
