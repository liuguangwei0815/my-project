package com.my.security.social.qq.connet;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

import com.my.security.social.qq.api.QQ;
import com.my.security.social.qq.api.QQUserInfo;

/**
 * 这个类存在的意义是适配api接口 QQ 供应商的的用户信息
 * @author Administrator
 *
 */
public class QQAdptor implements ApiAdapter<QQ> {

	@Override
	public boolean test(QQ api) {
		return false;
	}

	@Override
	public void setConnectionValues(QQ api, ConnectionValues values) {
		QQUserInfo user = api.getUserInfo();
		values.setDisplayName(user.getNickname());
		values.setImageUrl(user.getFigureurl_qq_1());
		values.setProfileUrl(null);//个人主页
		values.setProviderUserId(user.getOpenId());
	}

	@Override
	public UserProfile fetchUserProfile(QQ api) {
		return null;
	}

	@Override
	public void updateStatus(QQ api, String message) {
		// NOTHING
	}

}
