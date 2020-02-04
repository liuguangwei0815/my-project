/**
 * 
 */
package com.my.security.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.security.bean.AccountInfo;
import com.my.security.bean.LocalConfirmLog;
import com.my.security.mapper.AccountInfoMapper;
import com.my.security.mapper.LocalConfirmLogMapper;
import com.my.security.service.Ban2AccountService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuwei 李四加钱
 */
@Service
@Slf4j
public class Ban2AccountServiceImpl implements Ban2AccountService {

	@Autowired
	AccountInfoMapper accountInfoMapper;
	@Autowired
	private LocalConfirmLogMapper localConfirmLogMapper;

	@Override
	public void updateBan2Account(AccountInfo obj) {
		
	}

	/**
	 * // 因为是公共资源肯能被其他消费 比如加钱的时候这个必须放到commit 才能 因为commit 的意思是肯定会成功的
	 * ，即使这个本地事务异常了,也只会重试 不会执行cancle方法 也不会回滚 // 只能重试或者人工处理
	 * 
	 * @param obj
	 */
	@Transactional
	public void confirmMethod(AccountInfo obj) {
		// 获取全局事务XID
		String xid = "";//HmilyTransactionContextLocal.getInstance().get().getTransId();
		log.info("bank2 TCC commit  方法开始 xid:{}", xid);
		// 幂等
		LocalConfirmLog lcl = new LocalConfirmLog();
		lcl.setTxNo(xid);
		if (localConfirmLogMapper.selectCount(lcl) != 0) {
			log.info("bank1 TCC commit 校验幂等，commit方法已执行 xid：{}", xid);
			return;
		}
		// 张三减钱
		if (accountInfoMapper.addBalance(new AccountInfo("2", obj.getAccountBalance())) <= 0) {
			throw new RuntimeException("bank1 TCC try  张三加钱失败  xid：{}" + xid);
		}

		// 添加敏等记录
		lcl.setCreateTime(new Date());
		localConfirmLogMapper.insert(lcl);
	}


}
