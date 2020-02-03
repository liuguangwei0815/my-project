package com.my.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.security.bean.AccountInfo;
import com.my.security.service.Ban2AccountService;

@RestController
@RequestMapping("/bank2")
public class AccountInfoController {
	
	@Autowired
	public Ban2AccountService ban2AccountService;

	
	@PostMapping("/updateAccount")
	public boolean updateBank2Account(@RequestBody AccountInfo acc) {
		
		ban2AccountService.updateBan2Account(acc);
		return true;
	}

}
