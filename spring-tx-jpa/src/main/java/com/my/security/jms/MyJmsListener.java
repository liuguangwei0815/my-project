package com.my.security.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

/**
 * 监听
 * 
 * @author liuwei
 *
 */
@Service
@Slf4j
public class MyJmsListener {

	@Autowired
	private JmsTemplate jmsTemplate;

	/*
	 * @Transactional
	 * 
	 * @JmsListener(destination = "customer:msg1:new" ,containerFactory =
	 * "jmsFactory" ) // 监听这个队列customer:msg1:new ,设置监听工厂 public void
	 * receiveAndSend(String msg) { log.info("get msg1 :{}", msg);
	 * jmsTemplate.convertAndSend("customer:msg:receive","receive" + msg);
	 * if(msg.contains("error")) { throw new RuntimeException("send jms error"); } }
	 */

}
