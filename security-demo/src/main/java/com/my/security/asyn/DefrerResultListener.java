package com.my.security.asyn;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DefrerResultListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private MockQueue mockQueue;
	@Autowired
	private DefrerResultHolder defrerResultHolder;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		new Thread(() -> {
			while (true) {
				if (StringUtils.isNotBlank(mockQueue.getCompliOrder())) {
					log.info("处理订单");
					String orderNumber = mockQueue.getCompliOrder();
					defrerResultHolder.getMap().get(orderNumber).setResult("order is compliment");
					mockQueue.setCompliOrder(null);
					log.info("处理订单完毕");
				} else {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

}
