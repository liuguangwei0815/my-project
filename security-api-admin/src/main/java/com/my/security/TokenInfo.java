/**
 * 
 */
package com.my.security;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liuwei
 *
 */
@Data
@Slf4j
public class TokenInfo {

	private String access_token;
	private String token_type;
	private String refresh_token;
	private String expires_in;
	private String scope;
	
	private LocalDateTime expTime;
	
	public TokenInfo init() {
		expTime = LocalDateTime.now().plusSeconds(Long.parseLong(this.getExpires_in())-3);//过期时间减去三秒 因为有些是有时间精确 会导致时间可能已经过期 这里判断
		log.info("有效秒数:{}，过期时间为：{}",this.getExpires_in(),expTime);
		return this;
	}
	
	public boolean isExprie() {
		return expTime.isBefore(LocalDateTime.now());
	}
	
	
	
}
