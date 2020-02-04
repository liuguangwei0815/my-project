/**
 * 
 */
package com.my.security.rocketMqListenner;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.my.security.bean.AccountInfoTxMsg;
import com.my.security.service.Ban2AccountService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuwei 订阅端进行信息消费
 * @RocketMQMessageListener --> consumerGroup 定义一个订阅者组 这个名词可以自定义 但必须得有 topic
 *                          必须和发送方一致 否则服务监听 对应的top
 */
@Component
@RocketMQMessageListener(consumerGroup = "consumer_tx_group_bank2", topic = "tx_top_destination_bank1")
@Slf4j
public class CustomerRocketMqListener implements RocketMQListener<String> {

	@Autowired
	private Ban2AccountService ban2AccountService;

	@Override
	public void onMessage(String message) {
		log.info("rocketMq 订阅方  接受成功 -->开始。。。");
		// 将message 转换为 jsonObject对象
		JSONObject jobj = JSONObject.parseObject(message);
		AccountInfoTxMsg txmsg = JSON.parseObject(jobj.getString("accountmsg"), AccountInfoTxMsg.class);
		log.info("rocketMq 订阅方  接受成功 -->解析json:{}", ReflectionToStringBuilder.reflectionToString(txmsg));
		// 执行本地业务方法，本地业务方法必须实现幂等控制 不然会造成数据不一致 还有高并发下的 锁 也要加上 不然也会导致数据不一致
		ban2AccountService.updateBan2Account(txmsg);
	}

}
