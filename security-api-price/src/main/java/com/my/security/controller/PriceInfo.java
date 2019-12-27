package com.my.security.controller;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class PriceInfo {
	
	@NotBlank(message = "不允许为空")
	private String productId;
	private BigDecimal price;

}
