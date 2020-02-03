package com.my.security.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class AccountInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -588346392860399507L;
	private Long id;
	private String accountName;
	private String accountNo;
	private String accountPassword;
	private BigDecimal accountBalance;

	public AccountInfo() {

	}

	public AccountInfo(String accountNo, BigDecimal accountBalance) {
		this.accountNo = accountNo;
		this.accountBalance = accountBalance;
	}
	
	public AccountInfo(String accountNo, BigDecimal accountBalance,String accountName) {
		this.accountNo = accountNo;
		this.accountBalance = accountBalance;
		this.accountName = accountName;
	}

}
