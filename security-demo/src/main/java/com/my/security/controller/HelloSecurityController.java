package com.my.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloSecurityController {
	
	@GetMapping("/hello")
	public String hellowSecuriy() {
		return "hello securiy";
	}

}
