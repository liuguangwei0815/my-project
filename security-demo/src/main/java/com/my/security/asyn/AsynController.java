package com.my.security.asyn;

import java.util.concurrent.Callable;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/asy")
public class AsynController {

	@Autowired
	private MockQueue mockQueue;
	@Autowired
	private DefrerResultHolder defrerResultHolder;

	@GetMapping
	public String asy() throws InterruptedException {
		log.info("主线程开始");
		Thread.sleep(1000);
		log.info("主线程结束");
		return "success";
	}

	/**
	 * callable 处理异步处理restfull
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	@GetMapping("/callable")
	public Callable<String> asyCallable() throws InterruptedException {
		log.info("主线程开始");
		Callable<String> call = new Callable<String>() {

			@Override
			public String call() throws Exception {
				log.info("副线程开始");
				Thread.sleep(8000);
				log.info("副线程结束");
				return "success";
			}

		};
		log.info("主线程结束");
		return call;
	}

	/**
	 * callable 处理异步处理restfull
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	@GetMapping("/defres")
	public DeferredResult<String> defres() throws InterruptedException {
		log.info("主线程开始");
		String orderNumber = RandomStringUtils.randomNumeric(8);
		mockQueue.setPlaceOrder(orderNumber);
		DeferredResult<String> result = new DeferredResult<String>();
		defrerResultHolder.getMap().put(orderNumber, result);
		log.info("主线程结束");
		return result;
	}

}
