package com.my.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@RestController
@EnableZuulProxy
@Slf4j
public class SecurityApiAdminApplication {
	
	private RestTemplate restTemplate = new RestTemplate();
	
	/**
	 * 直接改造成授权码模式 直接这里登录就作废了
	 * @param credentials
	 * @param request
	 */
	@PostMapping("/login")
	public void login(@RequestBody Credentials credentials,HttpServletRequest request) {
		
		// 类似资源服务需要发送http请求去获取相关token的信息 然后通过返回值返回到我们的实体类中(网关获取)
		String checkTokenEndpointUrl = "http://localhost:7026/token/oauth/token";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setBasicAuth("adminServer", "123456");

		MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
		param.add("username",credentials.getUsername());
		param.add("password",credentials.getPassword());
		param.add("grant_type", "password");
		param.add("scope", "read write");
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(param, headers);
		ResponseEntity<TokenInfo>
		response = restTemplate.exchange(checkTokenEndpointUrl, HttpMethod.POST, entity,
				TokenInfo.class);
		log.info("tokenInfo:{}",response.getBody());
		//将token放到session中弄去
		request.getSession().setAttribute("tokenInfo", response.getBody());
	}
	@PostMapping("/logout")
	public void logout(HttpServletRequest request) {
		request.getSession().invalidate();
	}
	
	/**
	 * @RequestParam 这个是要去要求参数必须传递
	 * @param code
	 * @param request
	 * @throws IOException 
	 */
	@GetMapping("/oauth/callback")
	public void callback(@RequestParam String code,String state,HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		// 类似资源服务需要发送http请求去获取相关token的信息 然后通过返回值返回到我们的实体类中(网关获取)
		String checkTokenEndpointUrl = "http://security.geteway.com:7026/token/oauth/token";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setBasicAuth("adminServer", "123456");

		MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
		param.add("grant_type", "authorization_code");
		param.add("code", code);
		param.add("redirect_uri","http://security.admin.com:7027/oauth/callback");
		param.add("client_id","adminServer");
		//param.add("scope", "read write");
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(param, headers);
		ResponseEntity<TokenInfo>
		responseEnity = restTemplate.exchange(checkTokenEndpointUrl, HttpMethod.POST, entity,
				TokenInfo.class);
		TokenInfo newToken = responseEnity.getBody();
		log.info("tokenInfo:{}",newToken);
		//将token放到session中弄去
		request.getSession().setAttribute("tokenInfo", newToken.init());
		//数据库或者属性autoApprove  可以设置为true 那么默认全部授权通过 不需要用户自定义去点击按钮，或者输入read 或write 那么这个会默认授权
		
		//这里完全可以通过state 这个表示进行跳转页面的指定
		response.sendRedirect("/");
	}
	
	
	//通过这个去判断前端服务器是否已经授权通过了
	@GetMapping("/me")
	public TokenInfo me(HttpServletRequest request) {
		return (TokenInfo) request.getSession().getAttribute("tokenInfo");
	}
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(SecurityApiAdminApplication.class);
	}

}



