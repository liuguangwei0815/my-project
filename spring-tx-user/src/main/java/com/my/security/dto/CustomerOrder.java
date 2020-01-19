package com.my.security.dto;

import java.math.BigDecimal;

import lombok.Data;
@Data
public class CustomerOrder {

	private Long id;
	private String title;
	private String details;
	private BigDecimal amount;

}
