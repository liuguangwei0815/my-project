package com.my.security;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@RestController
@EnableZuulProxy
@Slf4j
public class SecurityApiAdminApplication {
	
	private RestTemplate restTemplate = new RestTemplate();
	
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
	
	public static void main(String[] args) {
		SpringApplication.run(SecurityApiAdminApplication.class);
	}

}



