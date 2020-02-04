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

import com.my.security.bean.AccountInfoTxMsg;
import com.my.security.service.Ban2AccountService;

/**
 * @author liuwei
 *
 */
@RestController
@RequestMapping("/bank2")
public class AccountController {

	@Autowired
	public Ban2AccountService ban2AccountService;
	
	
	@PostMapping("/pay")
	public String trans(@RequestBody AccountInfoTxMsg info) {

		AccountInfoTxMsg txMsg = new AccountInfoTxMsg();
		BeanUtils.copyProperties(info, txMsg);
		txMsg.setTxNo(StringUtils.replace(UUID.randomUUID().toString(), "-", ""));// 设置事务唯一ID,这个你可以结合数据可 去其他服务调用获取看或者是redis获取唯一的
		// 进行RocketMq事务发送操作
		ban2AccountService.recharge(txMsg);
		return "success";
	}
	
	//必须提供一个查询接口
	@PostMapping("/checkPay")
	public AccountInfoTxMsg checkPay(@RequestBody AccountInfoTxMsg info) {
		return ban2AccountService.checkResult(info.getTxNo());
	}
	

}
