/**
 * 
 */
package com.my.security.servive.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.security.bean.AccountInfo;
import com.my.security.bean.AccountInfoTxMsg;
import com.my.security.bean.DeDuplication;
import com.my.security.mapper.AccountInfoMapper;
import com.my.security.mapper.DeDuplicationMapper;
import com.my.security.servive.AccountService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuwei
 *
 */
@Service
@Slf4j
public class AccountServiceImpl implements AccountService,MqDefindedName {

	@Autowired
	private AccountInfoMapper accountInfoMapper;
	@Autowired
	private DeDuplicationMapper deDuplicationMapper;


	/**
	 * 本地事务操作
	 * 因为rocketMq server 不一定能接收到奥rocket 发送的的本地事务提交 ， 有重复通知 可能 所以需要实现幂等
	 */
	@Override
	@Transactional
	public void updateBank1Account(AccountInfoTxMsg obj) {
		// 幂等
		DeDuplication ded = new DeDuplication();
		ded.setTxNo(obj.getTxNo());
		if (deDuplicationMapper.selectCount(ded) > 0) {
			log.info("bank1本地事务已执行,txNo:{}",obj.getTxNo());
			return;
		}
		AccountInfo info = new AccountInfo("1", obj.getAccountBalance());
		if (accountInfoMapper.subBalance(info) <= 0) {
			throw new RuntimeException("本地事务 Bank1 扣减金额失败,txNo:" + obj.getAccountBalance());
		}
		// ，用来校验幂等记录
		ded.setCreateTime(new Date());
		deDuplicationMapper.insert(ded);
		
		if(obj.getAccountBalance().compareTo(new BigDecimal("1"))==0) {
			throw new RuntimeException("ban1 张三 server 内部业务方法（updateBank1Account）  人为制作异常");
		}
		
	}

}
