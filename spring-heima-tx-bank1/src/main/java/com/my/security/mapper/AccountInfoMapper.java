package com.my.security.mapper;

import com.my.security.bean.AccountInfo;

import tk.mybatis.mapper.common.Mapper;

public interface AccountInfoMapper extends Mapper<AccountInfo>{
	
	void updateAccountInfo(AccountInfo info);

}
