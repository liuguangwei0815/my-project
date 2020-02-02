/**
 * 
 */
package com.my.security.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.security.bean.AccountInfo;
import com.my.security.mapper.AccountInfoMapper;
import com.my.security.service.impl.Ban2AccountService;

import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liuwei
 *  李四加钱
 */
@Service
@Slf4j
public class Ban2AccountServiceImpl implements Ban2AccountService {
	
	
	@Autowired
	AccountInfoMapper accountInfoMapper;
	
	@Transactional
	@Override
	public void updateBan2Account(AccountInfo acc) {
		log.info("bank2 get XID:{}",RootContext.getXID());
		accountInfoMapper.updateAccountInfo(acc);
		
		if(StringUtils.equals(acc.getAccountName(), "1")) {
			throw new RuntimeException("人为制造bank2 异常");
		}
		
		
	}
	
	

}
