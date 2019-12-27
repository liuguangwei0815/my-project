package com.my.security.controller;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PriceInfo {
	
	private String productId;
	private BigDecimal price;

}
