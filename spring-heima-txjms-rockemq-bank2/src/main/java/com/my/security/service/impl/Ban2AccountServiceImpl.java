/**
 * 
 */
package com.my.security.service.impl;

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
import com.my.security.service.Ban2AccountService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuwei 李四加钱 这个需要实现幂等控制 ，以为如果mq server 么有收到 订阅方的ack 那么 server 会一直调用
 *         订阅方的方法
 */
@Service
@Slf4j
public class Ban2AccountServiceImpl implements Ban2AccountService {

	@Autowired
	private AccountInfoMapper accountInfoMapper;
	@Autowired
	private DeDuplicationMapper deDuplicationMapper;

	@Override
	@Transactional // 开启本地事务
	public void updateBan2Account(AccountInfoTxMsg obj) {
		log.info("bank2 开始执行本地业务方法开始 txNo :{}", obj.getTxNo());
		// 幂等控制
		DeDuplication ded = new DeDuplication();
		ded.setTxNo(obj.getTxNo());
		if (deDuplicationMapper.selectCount(ded) > 0) {
			log.info("bank2 本地事务已执行,txNo:{}", obj.getTxNo());
			return;
		}
		AccountInfo info = new AccountInfo("2", obj.getAccountBalance());
		// 执行本地业务
		if (accountInfoMapper.addBalance(info) < 00) {
			throw new RuntimeException("本地事务 Bank1 扣减金额失败,txNo:" + obj.getAccountBalance());
		}
		// 添加幂等记录
		ded.setCreateTime(new Date());
		deDuplicationMapper.insert(ded);
		
		if(obj.getAccountBalance().compareTo(new BigDecimal("2"))==0) {
			throw new RuntimeException("ban2 李四 人为制作异常");
		}

	}

}
