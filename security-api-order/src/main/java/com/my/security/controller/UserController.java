package com.my.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UserController {
	@GetMapping("/me")
	public Authentication getUserMe(Authentication user) {
		System.out.println("user==>"+user);
		return user;
	}
}
