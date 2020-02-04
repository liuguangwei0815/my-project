/**
 * 
 */
package com.my.security.servive.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
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
	private RocketMQTemplate rocketMQTemplate;
	@Autowired
	private DeDuplicationMapper deDuplicationMapper;
	@Autowired
	@Qualifier(value = "accountServiceImpl2")
	private AccountServiceImpl2 accountServiceImpl2;

	/**
	 * 发送消息事务
	 * 
	 * @param obj
	 */
	@Override
	public void sendRocketMqTxMsg(AccountInfoTxMsg obj) {
		/**
		 *  final String txProducerGroup, 事务生成组 这个组名不可以和我们配置文件的启动生产组名一样
		 *  final String
		 *  destination, top 消息主题
		 *  final Message<?> message, //消息内容
		 *  final Object arg //参数
		 */
		String txProducerGroup = DEFINEDNAME;// 事务生成组 这个组名不可以和我们配置文件的启动生产组名一样
		String destination = "tx_top_destination_bank1";//发送方 top 消息主题  订阅方监听的主题
		JSONObject jo = new JSONObject();
		jo.put("accountmsg", obj);
		Message<String> message = MessageBuilder.withPayload(jo.toJSONString()).build();// 消息内容
		String arg = null;// 参数
		rocketMQTemplate.sendMessageInTransaction(txProducerGroup, destination, message, arg);
		// 后续发送了之后 是否接受成功 事务在我们的RocketLocalTransactionList 的 exect 方法
	}

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
