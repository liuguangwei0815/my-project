/**
 * 
 */
package com.my.security.social.qq.connet;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;

import com.my.security.social.qq.api.QQ;

/**
 * @author Administrator
 *
 */
public class QQConnectFactory extends OAuth2ConnectionFactory<QQ> {

	public QQConnectFactory(String providerId,String appId,String appSecret) {
		super(providerId, new QQServiceProvider(appId, appSecret), new QQAdptor());
	}

}
