package com.my.security.controller;

import javax.validation.Valid;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {

	// private RestTemplate restTemplate = new RestTemplate();
	
	@Autowired
	private OAuth2RestTemplate oAuth2RestTemplate;

	private static final String RPURL = "http://localhost:7028/";

	/**
	 * 如果不做转换用户信息处理 那么只能获取 username ，如果做了转换 那么这个就不能这么获取了
	 * 
	 * @param order
	 * @param error
	 * @param username
	 * @return
	 */
//	@PostMapping
//	public Order getOrder(@Valid @RequestBody Order order,BindingResult error,@AuthenticationPrincipal String username) {
//		if(error.hasErrors()) {
//			error.getAllErrors().stream().map(e->buildError(e)).findFirst();
//		}
//		log.info("oauth2 获取用户信息:{}",username);
//		
//		//PriceInfo pi = restTemplate.getForObject(RPURL+"price/"+order.getProductId(), PriceInfo.class);
//		//log.info("获取产品id：{},价格：{}",order.getProductId(),pi.getPrice());
//		return  order;
//	}
//	
	/**
	 * 因为启动了网关模式 所以这里通过@RequestHeader 获取，这是个我们在授权filter通过赋值的
	 * 
	 * @param order
	 * @param error
	 * @param username
	 * @return
	 */
	@PostMapping
	public Order getOrder(@Valid @RequestBody Order order, BindingResult error, @RequestHeader String username) {
		if (error.hasErrors()) {
			error.getAllErrors().stream().map(e -> buildError(e)).findFirst();
		}
		log.info("oauth2 获取用户信息:{}", username);

		// PriceInfo pi = restTemplate.getForObject(RPURL+"price/"+order.getProductId(),
		// PriceInfo.class);
		// log.info("获取产品id：{},价格：{}",order.getProductId(),pi.getPrice());
		return order;
	}

//	@GetMapping("/getUserInfo1/{productId}")
//	public Order getUser1(@PathVariable String productId,@AuthenticationPrincipal MyUser user) {
//		log.info("MyUser：{}",user.getId());
//		return  new Order();
//	}
//	
//	@GetMapping("/getUserInfo2/{productId}")
//	public Order getUser2(@PathVariable String productId,@AuthenticationPrincipal(expression = "#this.id") Long userId) {
//		log.info("spring 表单时只获取其中一个属性：{}",userId);
//		return  new Order();
//	}

//	@GetMapping("/{productId}")
//	public Order getUser(@PathVariable Long productId, @RequestHeader String username) {
//		log.info("productId：{}", productId);
//		log.info("username：{}", username);
//		Order order = new Order();
//		order.setId(productId);
//		order.setProductId((Long)(productId * 5));
//		return order;
//	}
	
	
	@GetMapping("/{productId}")
	//@PreAuthorize("#oauth2.hasScope('fly')")
	//@PreAuthorize("hasRole('ROLE_USER')")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@SentinelResource(value = "getUser",blockHandler = "getUserBlock")
	public Order getUser(@PathVariable Long productId, @AuthenticationPrincipal String username) throws InterruptedException {
		
//		try (Entry entry = SphU.entry("getUser")){
//			log.error("getUser 正常流程。。。");
//		} catch (BlockException e) {
//			log.error("限流");
//		}
//		
		//为了测试降级 修庙50毫秒
		Thread.sleep(50);
		
		log.info("username jwt解析获取用户信息：{}", username);
		Order order = new Order();
		order.setId(productId);
		order.setProductId((Long)(productId * 5));
		PriceInfo pi = oAuth2RestTemplate.getForObject(RPURL+"price/"+order.getProductId(), PriceInfo.class);
		return order;
	}
	
	
	
	//如果是流控了或者降级了 会走这个逻辑
	public Order getUserBlock(@PathVariable Long productId, @AuthenticationPrincipal String username,BlockException exception)  {
		log.info("流控了，或者降级了 走了熔断的业务逻辑，"+exception.getClass().getSimpleName());
		Order order = new Order();
		order.setProductId((Long)(productId * 5));
		return order;
	}
	
	
	@GetMapping("/second/{productId}")
	@SentinelResource(value = "getOrderInfo")//声明资源
	public Order getOrderInfo(@PathVariable Long productId, @AuthenticationPrincipal String username){
		log.info("获取订单信息...");
		Order order = new Order();
		order.setId(productId);
		order.setProductId((Long)(productId * 5));
		
		throw new RuntimeException("hahaha");
		
		//return order;
	}

	private Object buildError(ObjectError e) {
		throw new RuntimeException(e.getDefaultMessage());
	}
}
