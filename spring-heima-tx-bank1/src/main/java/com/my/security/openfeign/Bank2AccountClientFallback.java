package com.my.security.openfeign;

import org.springframework.stereotype.Component;

import com.my.security.bean.AccountInfo;
@Component
public class Bank2AccountClientFallback implements Bank2AccountClient{

	public String updateAccount(AccountInfo acc) {
		return "rollback";
	}
}
