package com.my.security.controller;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/price")
@Slf4j
public class PriceController {
	@GetMapping("/{productId}")
	public PriceInfo getOrder(@Valid PriceInfo priceInfo,BindingResult error,@AuthenticationPrincipal String username) {
		log.info("获取价格,同时获取用户信息:{}",username);
		if(error.hasErrors()) {
			error.getAllErrors().stream().map(e->buildError(e)).findFirst();
		}
		PriceInfo price  = new PriceInfo();
		price.setProductId(priceInfo.getProductId());
		price.setPrice(new BigDecimal("1000"));
		return price;
	}

	private Object buildError(ObjectError e) {
		throw new RuntimeException(e.getDefaultMessage());
	}

}
