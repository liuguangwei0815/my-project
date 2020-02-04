/**
 * 
 */
package com.my.security.servive;

import com.my.security.bean.AccountInfoTxMsg;

/**
 * @author liuwei
 *
 */
public interface AccountService {

	void sendRocketMqTxMsg(AccountInfoTxMsg obj);

	void updateBank1Account(AccountInfoTxMsg obj);
	
}
