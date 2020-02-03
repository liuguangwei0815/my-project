/*
 *  Title：LocalTryLog.java
 *  CreateTime： 2015-06-24
 *  Copyright ©   e路同心（www.88bank.com）  All right reserved
 */
package com.my.security.bean;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;


@Data
public class LocalTradeLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6769872732076513516L;

	private String txNo; // 事务id
	
	private Date createTime; // 
	
}
