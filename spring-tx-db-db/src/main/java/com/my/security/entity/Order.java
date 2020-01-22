/**
 * 
 */
package com.my.security.entity;

import java.math.BigDecimal;

import lombok.Data;

/**
 * @author liuwei
 *
 */
@Data
public class Order {
	
	private Long id;
	private Long customerId;
	private String title;
	private BigDecimal amount;

}
