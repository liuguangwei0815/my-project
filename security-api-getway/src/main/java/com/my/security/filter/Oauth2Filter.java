///**
// * 
// */
//package com.my.security.filter;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Modifier;
//import java.time.LocalDateTime;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.commons.lang.StringUtils;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//
//import com.alibaba.fastjson.JSON;
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//import com.netflix.zuul.exception.ZuulException;
//
//import lombok.extern.slf4j.Slf4j;
//
///**
// * 将资源服务安全配置的逻辑移动到这里 ，因为资源服务器不能有任何安全的配置 所以讲逻辑挪到这了,这个是认证filter
// * 
// * @author liuwei 这是逻辑原理在这里，其实sprngsecurity 和 oauth 都帮我做了这些工作
// *
// */
//@Component
//@Slf4j
//public class Oauth2Filter extends ZuulFilter {
//
//	private RestTemplate restTemplate = new RestTemplate();
//
//	@Override
//	public boolean shouldFilter() {
//		// 是否开启过滤
//		return true;
//	}
//
//	@Override
//	public Object run() throws ZuulException { 
//		// 正确的过滤逻辑
//		log.info("oath2 start....");
//		// 如果请求的地址是token 那么直接放行，不做处理，因为他是直接去认证的
//		RequestContext context = RequestContext.getCurrentContext();
//		HttpServletRequest reqest = context.getRequest();
//
//		if (StringUtils.startsWith(reqest.getRequestURI(), "/token")) {
//			// 如果请求连接是直接去认证中心的 放行不做处理
//			return null;
//		}
//
//		String authorization = reqest.getHeader("Authorization");
//		if (StringUtils.isBlank(authorization)) {
//			// heard 没有 直接null 不做处理
//			return null;
//		}
//		if (!StringUtils.startsWith(authorization, "bearer ")) {
//			// heard 没有带有token的 不做处理
//			return null;
//		}
//
//		TokenInfo info = getTokenInfo(authorization);
//
//		reqest.setAttribute("tokenInfo", info);
//
//		log.info("oath2 end....");
//		return info;
//	}
//
//	private TokenInfo getTokenInfo(String authorization) {
//
//		String token = StringUtils.substringAfter(authorization, "bearer ");
//
//		// 类似资源服务需要发送http请求去获取相关token的信息 然后通过返回值返回到我们的实体类中
//		String checkTokenEndpointUrl = "http://auth.security.com:7024/oauth/check_token";
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//		headers.setBasicAuth("geteway", "123456");
//
//		MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
//		param.add("token", token);
//
//		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(param, headers);
//
//		ResponseEntity<TokenInfo> response = restTemplate.exchange(checkTokenEndpointUrl, HttpMethod.POST, entity,
//				TokenInfo.class);
//		
//		log.info("get token info :{}", response.getBody().toString());
//		log.info("时间对不上，所以进行转换");
//		
//		TokenInfo tokenInfo1 = response.getBody();
//		
//		long longTimeStamp = new Long(new Long(tokenInfo1.getExp().getTime()) * 1000);
//		Date date = new Date(longTimeStamp);
//		tokenInfo1.setExp(date);
//		tokenInfo1.setActive(new Date().before(tokenInfo1.getExp()));
//		log.info("final get token info :{}", tokenInfo1.toString());
//		return tokenInfo1;
//	}
//	
//	public static void main(String[] args) {
//		
//		 String str = "{\"aud\":[\"order-server\"],\"user_name\":\"123123\",\"scope\":[\"read\",\"write\"],\"exp\":1577687175000,\"authorities\":[\"admin\",\"ROLE_ADMIN\"],\"client_id\":\"orderApp\"}";
//		
//		 TokenInfo fromJson = JSON.parseObject(str, TokenInfo.class);
//		 
//		 System.out.println(fromJson);
//		 
//		 System.out.println(fromJson.getExp().getTime());
//		 
//		long longTimeStamp = new Long(new Long(1577687175) * 1000);
//		Date date = new Date(longTimeStamp);
//		System.out.println(date);
//	}
//
//	
//	@Override
//	public String filterType() {
//		// filter 的 类型 "pre"(逻辑之前) "post"（逻辑之后） "error"(当抛出异常之后) "route"(这个一般不用写 因为zuul
//		// 已经帮我实现了)
//		return "pre";
//	}
//
//	@Override
//	public int filterOrder() {
//		// 过滤器执行顺序
//		return 1;
//	}
//
//}
