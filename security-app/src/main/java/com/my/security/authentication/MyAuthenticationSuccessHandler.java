package com.my.security.authentication;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.security.properites.SecurityProperties;
import com.my.security.properites.enums.LoginType;

import lombok.extern.slf4j.Slf4j;

@Component("myAuthenticationSuccessHandler")
@Slf4j
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private SecurityProperties securityProperties;
	//获取DetailsService
	@Autowired
	private ClientDetailsService clientDetailsService;
	@Autowired
	private AuthorizationServerTokenServices tokenServices;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		log.info("登录成功 ! authenticaiton info : {}", mapper.writeValueAsString(authentication));
		log.info("登录类型：{}", securityProperties.getBrowser().getLoginType());
		//进行自己改造验证通过之而后可以自返回令牌 // 在basci 配置
		
		String header = request.getHeader("Authorization");
		if (header == null || !header.toLowerCase().startsWith("basic ")) {
			throw new UnapprovedClientAuthenticationException("basic 找不到");
		}
		String[] tokens = extractAndDecodeHeader(header, request);
		assert tokens.length == 2;
		String clientId = tokens[0];
		String clientSecert = tokens[1];
		//查询供应商的clienId 想对应的信息
		ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
		if(clientDetails==null) {
			throw new UnapprovedClientAuthenticationException("通过clientId："+clientId+"，查找ClientDetails不存在");
		}
		if(!StringUtils.equals(clientDetails.getClientSecret(), clientSecert)) {
			throw new UnapprovedClientAuthenticationException("clientSecert 不匹配");
		}
		//第一个参数 是为了组建Authentication 对象的参数我们这里不用 因为security 已经帮处理了
		TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId,clientDetails.getScope() , "custom");
		
		OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
		
		OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
		
		OAuth2AccessToken token = tokenServices.createAccessToken(oAuth2Authentication);
		
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(mapper.writeValueAsString(token));
		
	}
	
	
	/**
	 * Decodes the header into a username and password.
	 *
	 * @throws BadCredentialsException if the Basic header is not present or is not valid
	 * Base64
	 */
	private String[] extractAndDecodeHeader(String header, HttpServletRequest request)
			throws IOException {

		byte[] base64Token = header.substring(6).getBytes("UTF-8");
		byte[] decoded;
		try {
			decoded = Base64.getDecoder().decode(base64Token);
		}
		catch (IllegalArgumentException e) {
			throw new BadCredentialsException(
					"Failed to decode basic authentication token");
		}

		String token = new String(decoded, "UTF-8");

		int delim = token.indexOf(":");

		if (delim == -1) {
			throw new BadCredentialsException("Invalid basic authentication token");
		}
		return new String[] { token.substring(0, delim), token.substring(delim + 1) };
	}

}
