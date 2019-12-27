package com.my.security.controller;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class Order {
	
	@NotBlank(message = "productId 不能为空")
	private String productId;

}
