/**
 * 
 */
package com.my.security.servive.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.security.bean.AccountInfo;
import com.my.security.mapper.AccountInfoMapper;
import com.my.security.openfeign.Bank2AccountClient;
import com.my.security.servive.AccountService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuwei
 *
 */
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountInfoMapper accountInfoMapper;
	@Autowired
	private Bank2AccountClient bank2AccountClient;

	/**
	 * @Hmily 标识的方法就是try方法 controller 直接调用try方法即可，必须制定confirm方法和cancle方法 ,
	 *        方法参数和返回值必须一致，同事 如果在rpc 利用feign必须添加@Hmily 这个标签 否则 其他服务的事务无法执行 confirm
	 *        或者 cancel方法
	 **/
	@Override
	@Transactional // 需要开启本地事务
	public void updateBank1Account(AccountInfo obj) {

	}


}
