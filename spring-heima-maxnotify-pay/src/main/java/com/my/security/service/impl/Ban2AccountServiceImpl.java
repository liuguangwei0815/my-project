/**
 * 
 */
package com.my.security.service.impl;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.security.bean.AccountInfoTxMsg;
import com.my.security.service.Ban2AccountService;

import lombok.extern.slf4j.Slf4j;

/**
 * 最大努力通知 1、提供重试机制 ack 机制 2、如果不同通知 需要提供一个校验机制
 */
@Service
@Slf4j
public class Ban2AccountServiceImpl implements Ban2AccountService {

	@Autowired
	private RocketMQTemplate rocketMQTemplate;

	/**
	 * 进行充值并进行发送消息
	 * 
	 * @param obj
	 */
	@Override
	@Transactional // 开启本地事务
	public void recharge(AccountInfoTxMsg obj) {
		log.info("pay 开始充值并发送充值成功消息mq txNo :{}", obj.getTxNo());

		log.info("开支执行本地充值业务"); // 插入充值成功记录

		log.info("开支执行发送Mq消息"); // 如果充值成功进行发送

		String destination = "destination_pay_topic";
		rocketMQTemplate.convertAndSend(destination, obj);
	}

	@Override
	@Transactional // 开启本地事务
	public AccountInfoTxMsg checkResult(String txNO) {

		log.info("开始校验同步机制，保证结果校验");
		// 这里需要你将查询自己本地是否完成 并将结果返回回去
		return new AccountInfoTxMsg();

	}

}
