package com.my.security.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class AccountInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7444850132646071241L;
	/**
	 * 
	 */
	private Long id;
	private String accountName;
	private String accountNo;
	private String accountPassword;
	private Double accountBalance;
	

}
