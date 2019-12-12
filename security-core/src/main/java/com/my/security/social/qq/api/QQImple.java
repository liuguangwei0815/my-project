package com.my.security.social.qq.api;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QQImple extends AbstractOAuth2ApiBinding implements QQ {

	//获取用户信息的URL
	private static final String GET_QQ_USERINFOU_RIL = "https://graph.qq.com/user/get_user_info?" + 
			"oauth_consumer_key=%s&" + 
			"openid=%s";
	//获取用户openId的URL
	private static final String GET_QQ_OPENID_RIL ="https://graph.qq.com/oauth2.0/me?access_token=%s";
	
	
	private ObjectMapper mapper = new ObjectMapper();
	
	
	private String appId;
	private String openId;
	
	public QQImple(String accessToken,String appId) {
		//发请求时候会吧 access_token 放到请求连接上
		super(accessToken,TokenStrategy.ACCESS_TOKEN_PARAMETER);
		
		this.appId = appId;
		//获取OpenID
		String result = this.getRestTemplate().getForObject(String.format(GET_QQ_OPENID_RIL, accessToken), String.class);
		log.info("发送请求获取OpenID，结果为：{}",result);
		this.openId = StringUtils.substringBetween(result, "\"openid\":", "}");
	}
	
	
	
	/**
	 * 获取QQ用户信息
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@Override
	public QQUserInfo getUserInfo() {
		String result = this.getRestTemplate().getForObject(String.format(GET_QQ_USERINFOU_RIL,this.appId ,this.openId), String.class);
		log.info("发送请求获取用户信息，结果为：{}",result);
		try {
			return mapper.readValue(result, QQUserInfo.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
