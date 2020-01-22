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
public class Customer {

	private Long id;
	private String username;
	private BigDecimal deposit;//余额

}
