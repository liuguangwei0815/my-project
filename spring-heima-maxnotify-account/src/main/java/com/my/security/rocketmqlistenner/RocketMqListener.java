/**
 * 
 */
package com.my.security.rocketmqlistenner;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.my.security.bean.AccountInfoTxMsg;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuwei
 * 
 * 监听pay 的支付结果   
 *  consumerGroup : 这个是如果消费失败了 ，那么需要一直重试的consumerGroup 作为重试队列
 *  topic :  监听消费端的top
 */
@Component
@RocketMQMessageListener(consumerGroup="maxnotify_consumerGroup", topic = "destination_pay_topic")
@Slf4j
public class RocketMqListener implements RocketMQListener<AccountInfoTxMsg>{
	

	@Override
	public void onMessage(AccountInfoTxMsg message) {
		
		log.info("消费端 接收到信息：{}",ReflectionToStringBuilder.reflectionToString(message));
		
		log.info("进行幂等控制");
		
		log.info("进行本地业务方法");
		
		
		
	}

}
