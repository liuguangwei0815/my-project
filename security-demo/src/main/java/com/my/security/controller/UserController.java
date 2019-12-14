package com.my.security.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
	
	
	@PostMapping("/regist")
	public void regist(User user,HttpServletRequest request) {
		//return ResultVo.retSucc(SecurityContextHolder.getContext().getAuthentication());
		//获取用户的sesionId 唯一表示
		String userName = user.getUsername();
		//通过Social保存数据库
		providerSignInUtils.doPostSignUp(userName, new ServletWebRequest(request));
	}
	
	@GetMapping("/me")
	public ResultVo meinfocreate(Authentication authentication) {
		//return ResultVo.retSucc(SecurityContextHolder.getContext().getAuthentication());
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
