package com.my.security.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jms")
public class MyJmsController {
	
	
	@Autowired
	private MyJmsListener myJmsListener;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	/**
	 * 直接发送
	 * @param msg
	 */
	@GetMapping("/re")
	public void sendByDerect(String msg) {
		//myJmsListener.receiveAndSend(msg);
	}

	/**
	 * 直接发送
	 * @param msg
	 */
	@GetMapping
	public void send(String msg) {
		//向队列发送信息
		jmsTemplate.convertAndSend("customer:msg1:new",msg);
	}
	
	/**
	 * 查看信息
	 * @return 
	 */
	@GetMapping("/get")
	@Transactional
	public String get() {
		jmsTemplate.setReceiveTimeout(2000);//设置超时时间 否则会一直等待
		//向队列发送信息
		return String.valueOf(jmsTemplate.receiveAndConvert("customer:msg:aaa"));
	}
	
}
