package com.my.security;

import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
 * 1、请求头添加token，基于cookie获取方式 2、基于cookie token到期后刷新token
 * 
 * @author liuwei
 *
 */
@Component
@Slf4j
public class CookieAddTokenFilter extends ZuulFilter {

	private RestTemplate restTemplate = new RestTemplate();

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletResponse response = requestContext.getResponse();
		String access_token = getCookie("access_token");
		if (StringUtils.isNotBlank(access_token)) {
			requestContext.addZuulRequestHeader("Authorization", "bearer " + access_token);
		} else {
			String refresh_token = getCookie("refresh_token");
			if (StringUtils.isNotBlank(refresh_token)) {

				// 刷新令牌
				log.info("无法从cookie 获取token  ，现在开始刷新令牌");
				// 属性令牌
				// 类似资源服务需要发送http请求去获取相关token的信息 然后通过返回值返回到我们的实体类中
				String checkTokenEndpointUrl = "http://geteway.security.com:7026/token/oauth/token";

				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				headers.setBasicAuth("adminServer", "123456");

				MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
				param.add("grant_type", "refresh_token");
				param.add("refresh_token", refresh_token);

				HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(param, headers);

				try {

					ResponseEntity<TokenInfo> responseReflash = restTemplate.exchange(checkTokenEndpointUrl,
							HttpMethod.POST, entity, TokenInfo.class);
					log.info("刷新令牌获取 get token info :{}", responseReflash.getBody().toString());

					TokenInfo newToken = responseReflash.getBody();
					newToken.init();

					// 将token放到session中弄去
					// request.getSession().setAttribute("tokenInfo", newToken);
					Cookie cookie = new Cookie("access_token", newToken.getAccess_token());
					cookie.setDomain("security.com");// 设置了一级域名，那么他旗下的二级或者其他都生效，就是以security.com 的域名都会设置这个cookie
					cookie.setMaxAge(Integer.parseInt(newToken.getExpires_in()));
					cookie.setPath("/");// 设置在根目录
					response.addCookie(cookie);

					Cookie cookieReflash = new Cookie("refresh_token", newToken.getRefresh_token());
					cookieReflash.setDomain("security.com");// 设置了一级域名，那么他旗下的二级或者其他都生效，就是以security.com 的域名都会设置这个cookie
					cookieReflash.setMaxAge(2592000);
					cookieReflash.setPath("/");// 设置在根目录
					response.addCookie(cookieReflash);

					access_token = newToken.getAccess_token();

					requestContext.addZuulRequestHeader("Authorization", "bearer " + access_token);

				} catch (Exception e) {
					log.error("属性令牌异常：{}", e.getMessage());
					requestContext.setResponseStatusCode(500);
					requestContext.getResponse().setContentType("application/json;charset=UTF-8");
					requestContext.setResponseBody("{\"message\":\"refresh fail\"}");
					requestContext.setSendZuulResponse(false);
				}

			} else {
				requestContext.getResponse().setContentType("application/json;charset=UTF-8");
				requestContext.setResponseStatusCode(500);
				requestContext.setResponseBody("{\"message\":\"refresh fail\"}");
				requestContext.setSendZuulResponse(false);
			}

		}

		return null;
	}

	private String getCookie(String ckey) {
		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = requestContext.getRequest();
		Cookie[] cs = request.getCookies();
		//Optional<Cookie> oc = Arrays.stream(cs).filter(e -> StringUtils.equals(e.getName(), ckey)).findFirst();
		for (Cookie cookie : cs) {
			if(StringUtils.equals(cookie.getName(),ckey)) {
				return cookie.getValue();
			}
		}
		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;// 之前有session filter 0，
	}

}
