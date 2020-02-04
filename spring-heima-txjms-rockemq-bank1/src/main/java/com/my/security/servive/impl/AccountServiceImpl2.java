/**
 * 
 */
package com.my.security.servive.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.my.security.bean.AccountInfoTxMsg;
import com.my.security.bean.DeDuplication;
import com.my.security.mapper.DeDuplicationMapper;

/**
 * @author liuwei
 *  纯属测试 车市嵌入事务nesteded 
 */
@Service("accountServiceImpl2")
public class AccountServiceImpl2  {

	@Autowired
	private DeDuplicationMapper deDuplicationMapper;


	/**
	 * 嵌入事务 内部事务异常不影响 外部事务
	 * @param obj
	 */
	@Transactional
	public void testNesteded(AccountInfoTxMsg obj) {
		// 幂等
		DeDuplication ded = new DeDuplication();
		ded.setTxNo(obj.getTxNo()+RandomUtils.nextInt(100));
		// ，用来校验幂等记录
		ded.setCreateTime(new Date());
		deDuplicationMapper.insert(ded);
		
		if(obj.getAccountBalance().compareTo(new BigDecimal("3"))==0) {
			throw new RuntimeException("ban1 张三 server 内部业务方法（testNesteded）  人为制作异常");
		}
		
	}

}
