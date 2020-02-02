package com.my.security.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.my.security.bean.AccountInfo;
import com.my.security.service.impl.Ban2AccountService;

@RestController
@RequestMapping("/bank2")
public class AccountInfoController {
	
	@Autowired
	public Ban2AccountService ban2AccountService;
	
	@PostMapping("/updateAccount")
	public String updateBank2Account(@RequestBody AccountInfo acc) {
		
		ban2AccountService.updateBan2Account(acc);
		
		if(StringUtils.equals(acc.getAccountName(), "1")) {
			throw new RuntimeException("bank2 制造异常");
		}
		return "bank2"+JSON.toJSONString(acc);
	}

}
