package com.my.security.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.fasterxml.jackson.annotation.JsonView;
import com.my.security.aspect.TimerAntiontation;
import com.my.security.dto.ResultVo;
import com.my.security.dto.User;
import com.my.security.properites.SecurityProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Scope("prototype")
@Slf4j
@RequestMapping("/user")
public class UserController {
	@Autowired
	private ProviderSignInUtils providerSignInUtils;
	@Autowired
	private SecurityProperties securityProperties;
	
	
	@PostMapping("/regist")
	public void regist(User user,HttpServletRequest request) {
		//return ResultVo.retSucc(SecurityContextHolder.getContext().getAuthentication());
		//获取用户的sesionId 唯一表示
		String userName = user.getUsername();
		//通过Social保存数据库
		providerSignInUtils.doPostSignUp(userName, new ServletWebRequest(request));
	}
	
	@GetMapping("/me")
	public ResultVo meinfocreate(Authentication authentication,HttpServletRequest request) throws Exception {
		//return ResultVo.retSucc(SecurityContextHolder.getContext().getAuthentication());
		String header = request.getHeader("Authorization");
		String token = StringUtils.substringAfter(header, "bearer ");
		log.info("获取jwt秘钥：{}",securityProperties.getOauth2().getSigningKey());
		Claims claims = Jwts.parser().setSigningKey(securityProperties.getOauth2().getSigningKey().getBytes("UTF-8"))
				.parseClaimsJws(token).getBody();
		String enhancerValue = (String) claims.get("MyEnhancerInfo");
		log.info("enhancerKey:{},enhancerValue:{}","MyEnhancerInfo",enhancerValue);
		return  ResultVo.retSucc(authentication);
	}

	@PostMapping
	public ResultVo create(@Valid @RequestBody User user, BindingResult errors) {
		if (errors.hasErrors()) {
			return errors.getAllErrors().stream().map(e -> ResultVo.retFailMsg(e.getDefaultMessage())).findFirst()
					.get();
		}
		user.setId(1);
		log.info("reUser==>:", ReflectionToStringBuilder.reflectionToString(user));
		return ResultVo.retSucc(user);
	}
	
	@PutMapping("/{id:\\d+}")
	public ResultVo update(@Valid @RequestBody User user, BindingResult errors) {
		if (errors.hasErrors()) {
			return errors.getAllErrors().stream().map(e -> ResultVo.retFileErorMsg(e)).findFirst()
					.get();
		}
		user.setId(1);
		log.info("reUser==>:", ReflectionToStringBuilder.reflectionToString(user));
		return ResultVo.retSucc(user);
	}
	
	@DeleteMapping("/{id:\\d+}")
	@ApiIgnore
	public ResultVo dele(@Valid @RequestBody User user,BindingResult errors) {
		if (errors.hasErrors()) {
			return errors.getAllErrors().stream().map(e -> ResultVo.retFileErorMsg(e)).findFirst()
					.get();
		}
		user.setId(1);
		log.info("reUser==>:", ReflectionToStringBuilder.reflectionToString(user));
		return ResultVo.retSucc(user);
	}
	
	

	@GetMapping
	@JsonView(User.UserSimpleView.class)
	@ApiOperation("用户列表查询服务")
	public List<User> list(@ApiParam("用户姓名")@RequestParam String userName) {
		log.info("接收参数：{}", userName);
		List<User> list = new ArrayList<User>();
		User user1 = new User();
		User user2 = new User();
		User user3 = new User();
		user1.setUsername("bb");
		log.info("利用反射工具反射对象：{}", ReflectionToStringBuilder.toString(user1, ToStringStyle.MULTI_LINE_STYLE));
		list.add(user1);
		list.add(user2);
		list.add(user3);
		return list;
	}

	@GetMapping("/{id:\\d+}")
	@TimerAntiontation
	@JsonView(User.UserDetailView.class)
	public User detail(@PathVariable String id) {
		//throw new UserNoExistException(1);
		log.info("进入用户详情方法");
		User user = new User();
		user.setUsername("tom");
		return user;
	}

	
	
	
	
}
