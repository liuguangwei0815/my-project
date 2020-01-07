/**
 * 
 */
package com.my.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuwei 该过滤器都会直接过来配置文件的rutes 配置， zuul可以将所有请求都token信息带着请求头  请求资源
 */
@Component
@Slf4j
public class RequestTrunFilter extends ZuulFilter {
	
	
	private RestTemplate restTemplate = new RestTemplate();

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = requestContext.getRequest();
		TokenInfo token = (TokenInfo) request.getSession().getAttribute("tokenInfo");
		//如果不为空 放到请求头去
		if (token != null) {
			
			String access_token = token.getAccess_token();
			
			//判断token是否过期，如果过期重新refresh token
			if(token.isExprie()) {
				log.info("token 已过期 ，现在开始刷新令牌");
				//属性令牌
				// 类似资源服务需要发送http请求去获取相关token的信息 然后通过返回值返回到我们的实体类中
				String checkTokenEndpointUrl = "http://security.geteway.com:7026/token/oauth/token";

				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				headers.setBasicAuth("adminServer", "123456");

				MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
				param.add("grant_type", "refresh_token");
				param.add("refresh_token", token.getRefresh_token());

				HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(param, headers);

				ResponseEntity<TokenInfo> response = restTemplate.exchange(checkTokenEndpointUrl, HttpMethod.POST, entity,
						TokenInfo.class);
				log.info("刷新令牌获取 get token info :{}", response.getBody().toString());
				
				TokenInfo newToken = response.getBody();
				newToken.init();
				//将token放到session中弄去
				request.getSession().setAttribute("tokenInfo", newToken);
				
				access_token = newToken.getAccess_token();
				
			}
			
			
			
			requestContext.addZuulRequestHeader("Authorization", "bearer "+access_token);
		}
		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

}
