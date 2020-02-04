/**
 * 
 */
package com.my.security.rocketmqlistenner;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.my.security.bean.AccountInfoTxMsg;
import com.my.security.bean.DeDuplication;
import com.my.security.mapper.DeDuplicationMapper;
import com.my.security.servive.AccountService;
import com.my.security.servive.impl.MqDefindedName;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuwei RocketMq 1、提交事务消息之后成功回调 2、事务回查。因为在提交事务消息 之后 和 mq server 之间的通信
 *         因为网络原因 导致无法正常接收 回调，mq server 进行主动事务回查，确定本地事务是否已经提交了
 */
@Component
@Slf4j
@RocketMQTransactionListener(txProducerGroup = MqDefindedName.DEFINEDNAME) // 监听该事务生产组
public class RocketMqLocalTxListener implements RocketMQLocalTransactionListener {

	@Autowired
	private AccountService accountService;
	@Autowired
	private DeDuplicationMapper deDuplicationMapper;

	/**
	 * rocket 发送方 发给 mq server ，mq server 接收到信息 并返回 会通过这个监听方法，需要这个监听方法实现本地事务 并返回给mq
	 * server 本地事务执行完毕， 如果本地事务自动提交和Mq server 网络原因 mq server 没有接受到本地事务是否提交的状态 那么就会mq
	 * server 主动 查询我们本事事务是否已经提交了 会调用我们的 checkLocalTransaction 执行本地事务
	 */
	@Override
	@Transactional
	public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {

		log.info("rocketMq server 接受成功 并且回调-->开始。。。");
		try {
			// 回调 将message 进行解析 一定要转出byte 然后 在转出string 否则回报转换异常
			String jsonStr = new String((byte[]) msg.getPayload());
			// 将string 转为为我们的JSonObject 对象
			JSONObject objjson = JSONObject.parseObject(jsonStr);
			// java.lang.ClassCastException 如果这样子写
			// AccountInfoTxMsg accmsg = (AccountInfoTxMsg) objjson.get("accountmsg");//
			// 对应的key
			// 需要这样子
			AccountInfoTxMsg accmsg = JSON.parseObject(objjson.getString("accountmsg"), AccountInfoTxMsg.class);

			log.info("rocketMq server 接受成功 并且回调 -->解析 json strin 并获取对象： {}",
					ReflectionToStringBuilder.reflectionToString(accmsg));
			// 执行本地业务
			accountService.updateBank1Account(accmsg);
			
			if(accmsg.getAccountBalance().compareTo(new BigDecimal("4"))==0) {
				log.info("模拟网络超时，看下mq server 的事务回滚");
				int sleepTime = Integer.parseInt(accmsg.getAccountName());
				log.info("本地事务方法开始sleep ： {} 分钟",sleepTime);
				Thread.sleep(1000*60*sleepTime);
			}
			log.info("rocketMq server 接受成功 并且回调 -->开始通知事务进行提交。。。");
			// 通知
			return RocketMQLocalTransactionState.COMMIT;
		} catch (Exception e) {
			log.error("rocketMq server 接受成功 并且回调 -->执行本地事务异常，通知通知事务进行回滚：{}", e);
			// 通知rocketMq server 进行事务回滚
			return RocketMQLocalTransactionState.ROLLBACK;
		}
	}

	/**
	 * 事务回查，MqServer 因为网络原因 或者其他原因 未收到本地事务的提交、或者回滚 相应，mq server
	 * 自主进行事务回查，查询本地是否已经提交，提交的标识是判断盖是否在本地史昂是否已有记录 ，这个可能会被Mq server 调用 多次
	 */
	@Override
	public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
		try {

			log.info("rocketMq server 本地事务回查-->开始。。。");

			// 回调 将message 进行解析 一定要转出byte 然后 在转出string 否则回报转换异常
			String jsonStr = new String((byte[]) msg.getPayload());
			// 将string 转为为我们的JSonObject 对象
			JSONObject objjson = JSONObject.parseObject(jsonStr);
			// java.lang.ClassCastException 如果这样子写
			// AccountInfoTxMsg accmsg = (AccountInfoTxMsg) objjson.get("accountmsg");//
			// 对应的key
			// 需要这样子
			AccountInfoTxMsg accmsg = JSON.parseObject(objjson.getString("accountmsg"), AccountInfoTxMsg.class);
			
			log.info("rocketMq server 接受成功 并且回调 -->解析 json strin 并获取对象： {}",
					ReflectionToStringBuilder.reflectionToString(accmsg));
			
			DeDuplication ded = new DeDuplication();
			ded.setTxNo(accmsg.getTxNo());
			if (deDuplicationMapper.selectCount(ded) > 0) {
				log.info("查询本地事务已提交，返回commit");
				return RocketMQLocalTransactionState.COMMIT;
			} else {
				log.info("查询本地事务已提交，返回unknown");
				return RocketMQLocalTransactionState.UNKNOWN;
			}
		} catch (Exception e) {
			log.info("事务回查查异常，", e);
			return RocketMQLocalTransactionState.UNKNOWN;
		}
	}

}
