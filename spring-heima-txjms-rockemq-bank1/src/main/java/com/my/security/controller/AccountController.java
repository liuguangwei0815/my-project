/**
 * 
 */
package com.my.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.security.bean.AccountInfo;
import com.my.security.servive.AccountService;

/**
 * @author liuwei
 *
 */
@RestController
@RequestMapping("/bank1")
public class AccountController {
	
	@Autowired
	public AccountService accountService;
	
	
	@PostMapping("/trans")
	public String trans(@RequestBody AccountInfo info) {
		accountService.updateBank1Account(info);
		return "success";
	}
	

}
