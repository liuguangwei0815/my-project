package com.my.security.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * 事务的tx msg 数据类
 * @author liuwei
 *
 */
@Data
public class AccountInfoTxMsg implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2944614004596637667L;
	/**
	 * 
	 */
	private String accountName;
	private String accountNo;
	private String accountPassword;
	private BigDecimal accountBalance;
	private String txNo;//事务号 全局唯一 做幂等判断

	public AccountInfoTxMsg() {

	}

	public AccountInfoTxMsg(String accountNo, BigDecimal accountBalance,String txNo) {
		this.accountNo = accountNo;
		this.accountBalance = accountBalance;
		this.txNo = txNo;
	}
	

}
