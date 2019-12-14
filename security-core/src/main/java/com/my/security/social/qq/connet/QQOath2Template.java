package com.my.security.social.qq.connet;

import java.nio.charset.Charset;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * 以为Social 默认返回的JSON  但是QQ返回的是 字符串的 类似地址以&连接起来的 所以需要改造 和  获取参数的方法方式
 * @author Administrator
 *
 */
public class QQOath2Template extends OAuth2Template {

	public QQOath2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
		super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
		this.setUseParametersForClientAuthentication(true);
	}
	
	
	

	/**
	 * 因为spring 默认是通过Map的get方式获取 这个方法也必须重写才能获取qq
	 */
	@Override
	protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
		String result = this.getRestTemplate().postForObject(accessTokenUrl, parameters,String.class);
		String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(result, "&");
		String accessToken = StringUtils.substringAfterLast(items[0], "=");
		long expiresIn = Long.parseLong(StringUtils.substringAfterLast(items[1], "="));
		String refreshToken = StringUtils.substringAfterLast(items[2], "=");
		return new AccessGrant(accessToken, null, refreshToken, expiresIn);
	}




	@Override
	protected RestTemplate createRestTemplate() {
		RestTemplate rest = super.createRestTemplate();
		rest.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		return rest;
	}
	
	

}
