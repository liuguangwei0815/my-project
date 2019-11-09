package com.my.security.browser.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("login-in.html")
	public String login() {
		return "/login/login-in";
	}
	
}
