/**
 * 
 */
package com.my.security.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuwei
 *
 */
@RestController
@RequestMapping("/bank1")
public class AccountController {

	// 主动查询接口
	@PostMapping("/checkResult")
	public String checkResult() {
		// 主动查询支付服务的结构并更新账户
		return null;
	}

}
