package com.my.security.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyLoginController {

	@GetMapping("/demo/login-demo.html")
	public String login() {
		return "/login/login-demo";
	}
	
}
