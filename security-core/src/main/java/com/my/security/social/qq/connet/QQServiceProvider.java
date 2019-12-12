package com.my.security.social.qq.connet;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

import com.my.security.social.qq.api.QQ;
import com.my.security.social.qq.api.QQImple;

/**
 * social qq服务提供
 * 
 * @author Administrator
 *
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

	//引导用户前往服务上认证服务的认证地址
	private static final String AUTHORIZEURL = "https://graph.qq.com/oauth2.0/authorize";
	//第三方用authentication code  去服务上申请 令牌的url
	private static final String ACCESSTOKENURL = "https://graph.qq.com/oauth2.0/token";
	
	private String appId;

	public QQServiceProvider(String appId, String appSecret) {
		super(new OAuth2Template(appId, appSecret, AUTHORIZEURL, ACCESSTOKENURL));
		//this.appId = appId;
	}

	@Override
	public QQ getApi(String accessToken) {
		return new QQImple(accessToken, appId);
	}

}
