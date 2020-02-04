/**
 * 
 */
package com.my.security.controller;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.security.bean.AccountInfo;
import com.my.security.bean.AccountInfoTxMsg;
//github.com/liuwei0815/my-security-oauth2.git
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

		AccountInfoTxMsg txMsg = new AccountInfoTxMsg();
		BeanUtils.copyProperties(info, txMsg);
		txMsg.setTxNo(StringUtils.replace(UUID.randomUUID().toString(), "-", ""));// 设置事务唯一ID,这个你可以结合数据可 去其他服务调用获取看或者是redis获取唯一的
		// 进行RocketMq事务发送操作
		accountService.sendRocketMqTxMsg(txMsg);
		return "success";
	}

}
