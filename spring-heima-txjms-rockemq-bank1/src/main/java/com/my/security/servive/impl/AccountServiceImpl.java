/**
 * 
 */
package com.my.security.servive.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.security.bean.AccountInfo;
import com.my.security.bean.DeDuplication;
import com.my.security.bean.LocalConfirmLog;
import com.my.security.bean.LocalTryLog;
import com.my.security.mapper.AccountInfoMapper;
import com.my.security.mapper.LocalCancelLogMapper;
import com.my.security.mapper.LocalConfirmLogMapper;
import com.my.security.mapper.LocalTryLogMapper;
import com.my.security.openfeign.Bank2AccountClient;
import com.my.security.servive.AccountService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuwei
 *
 */
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountInfoMapper accountInfoMapper;
	@Autowired
	private Bank2AccountClient bank2AccountClient;
	@Autowired
	private LocalConfirmLogMapper localConfirmLogMapper;
	@Autowired
	private LocalTryLogMapper localTryLogMapper;
	@Autowired
	private LocalCancelLogMapper localCancelLogMapper;

	/**
	 * @Hmily 标识的方法就是try方法 controller 直接调用try方法即可，必须制定confirm方法和cancle方法 ,
	 *        方法参数和返回值必须一致，同事 如果在rpc 利用feign必须添加@Hmily 这个标签 否则 其他服务的事务无法执行 confirm
	 *        或者 cancel方法
	 **/
	@Override
	@Transactional // 需要开启本地事务
	public void updateBank1Account(AccountInfo obj) {

		// 获取全局事务XID
		String xid = "";//HmilyTransactionContextLocal.getInstance().get().getTransId();
		log.info("bank1 TCC try 方法开始", xid);
		// 幂等操作，因为是但都线程调用 为了数据的一致性要实现幂等
		LocalTryLog ltl = new LocalTryLog();
		ltl.setTxNo(xid);
		if (localTryLogMapper.selectCount(ltl) != 0) {
			log.info("bank1 TCC try 校验幂等，try方法已执行 xid：{}", xid);
			return;
		}
		// 悬挂，防止网络原因 造成try 超时，try 悬挂，TM 任务 try 调用失败 然后后tm 执行cacel 操作 ，cancel 执行先于try
		LocalConfirmLog lcl = new LocalConfirmLog();
		lcl.setTxNo(xid);
		DeDuplication lcanl = new DeDuplication();
		lcanl.setTxNo(xid);
		if (localConfirmLogMapper.selectCount(lcl) != 0 || localCancelLogMapper.selectCount(lcanl) != 0) {
			log.info("bank1 TCC try 校验try悬挂，存在confirm 或者 cancle 已存在 执行过的记录，xid：{}", xid);
			return;
		}

		log.info("bank1 TCC try feign rest 请求 bank2，xid：{}", xid);

		boolean result = bank2AccountClient
				.updateAccount(new AccountInfo("1", obj.getAccountBalance(), obj.getAccountName()));

		if (!result) {
			throw new RuntimeException("bank1 TCC try  调用bank2 李四加钱失败 xid：{}" + xid);
		}
		// 张三减钱
		if (accountInfoMapper.subBalance(new AccountInfo("1", obj.getAccountBalance())) <= 0) {
			throw new RuntimeException("bank1 TCC try  张三减失败  xid：{}" + xid);
		}

		// 记录try幂等数据
		ltl.setCreateTime(new Date());
		localTryLogMapper.insert(ltl);

		// 张三服务器异常
		if (obj.getAccountBalance().compareTo(new BigDecimal("1")) == 0) {
			throw new RuntimeException("bank1 TCC try  张三  人造异常 xid：{}" + xid);
		}

		log.info("bank1 TCC try 方法结束", xid);

	}


}
