package com.my.security.controller;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class Order {
	
	@NotNull(message = "productId 不能为空")
	private Long productId;
	private Long id;

}
