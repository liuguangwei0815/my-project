package com.my.security.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.my.security.dto.User;

@RestController
@Scope("prototype")
public class UserController {
	
	
	@RequestMapping(value = "/user",method = RequestMethod.GET)
	public List<User> list(){
		List<User> list = new ArrayList<User>();
		User user1 = new User();
		User user2 = new User();
		User user3 = new User();
		list.add(user1);
		list.add(user2);
		list.add(user3);
		return list;
	}

}
