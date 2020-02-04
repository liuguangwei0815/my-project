package com.my.security.service;

import com.my.security.bean.AccountInfoTxMsg;

public interface Ban2AccountService {

	void recharge(AccountInfoTxMsg obj);

	AccountInfoTxMsg checkResult(String txNO);



}
