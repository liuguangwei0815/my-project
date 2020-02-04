package com.my.security.mapper;

import com.my.security.bean.AccountInfo;

import tk.mybatis.mapper.common.Mapper;

public interface AccountInfoMapper extends Mapper<AccountInfo>{
	
	/**
	 * 减少金额
	 * @param info
	 */
	int subBalance(AccountInfo info);
	/**
	 * 增加金额
	 */
	int addBalance(AccountInfo info);

}
