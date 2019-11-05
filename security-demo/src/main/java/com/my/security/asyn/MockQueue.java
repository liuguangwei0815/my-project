package com.my.security.asyn;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MockQueue {

	private String placeOrder;
	private String compliOrder;

	public String getPlaceOrder() {
		return placeOrder;
	}

	public void setPlaceOrder(String placeOrder) throws InterruptedException {
		new Thread(() -> {
			log.info("进行下单开始");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.compliOrder = placeOrder;
			log.info("进行下单结束:{}", placeOrder);
		}).start();
	}

	public String getCompliOrder() {
		return compliOrder;
	}

	public void setCompliOrder(String compliOrder) {
		this.compliOrder = compliOrder;
	}

}
