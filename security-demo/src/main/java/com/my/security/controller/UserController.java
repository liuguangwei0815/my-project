package com.my.security.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.context.annotation.Scope;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.my.security.dto.User;

import lombok.extern.slf4j.Slf4j;

@RestController
@Scope("prototype")
@Slf4j
@RequestMapping("/user")
public class UserController {
	
	
	@PostMapping
	@JsonView(User.UserSimpleView.class)
	public User create(@RequestBody User user) {
		log.info("利用反射工具反射对象：{}",ReflectionToStringBuilder.reflectionToString(user));
		user.setId(1);
		return user;
	}
	
	
	@GetMapping
	@JsonView(User.UserSimpleView.class)
	public List<User> list(@RequestParam String userName){
		log.info("接收参数：{}",userName);
		List<User> list = new ArrayList<User>();
		User user1 = new User();
		User user2 = new User();
		User user3 = new User();
		user1.setUserName("bb");
		log.info("利用反射工具反射对象：{}",ReflectionToStringBuilder.toString(user1, ToStringStyle.MULTI_LINE_STYLE));
		list.add(user1);
		list.add(user2);
		list.add(user3);
		return list;
	}
	
	
	@GetMapping("/{id:\\d+}")
	@JsonView(User.UserDetailView.class)
	public User detail(@PathVariable String id){
		User user  = new User();
		user.setUserName("tom");
		return user;
	}
	
	

}
