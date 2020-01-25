/**
 * 
 */
package com.my.security.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.security.dao.CustomorResposity;
import com.my.security.entity.Customer;
import com.my.security.entity.Order;

/**
 * @author liuwei
 *
 */
@Service
public class CustomerService {

	
	@Autowired
	private CustomorResposity customorResposity;

	@Autowired
	@Qualifier("orderJdbcTemplate")
	private JdbcTemplate orderJdbcTemplate;

	private String createOrderSql = "insert into `order` (customer_id,title,amount) VALUES (?,?,?)";

	@Transactional
	public Order creaeteOrder(Order order) {
		
		Customer db = customorResposity.findById(order.getCustomerId()).orElse(null);
		db.setDeposit(db.getDeposit().subtract(order.getAmount()));
		customorResposity.save(db);
		if(StringUtils.contains(order.getTitle(), "error1")) {//如果这里异常了 这里会会馆 user
			throw new RuntimeException("user update error");
		}
		// 插入订单
		orderJdbcTemplate.update(createOrderSql, order.getCustomerId(), order.getTitle(), order.getAmount());
		if(StringUtils.contains(order.getTitle(), "error2")) {//如果这里错了 user 会回滚 但是 order 不会回滚
			throw new RuntimeException("order update error");
		}
		
		return order;
		
	}

	public Map<String, Object> getInfo(Long customerId) {

		Customer db = customorResposity.findById(customerId).orElse(null);
		List<Map<String, Object>> list = orderJdbcTemplate
				.queryForList("select * from `order` where customer_id = " + customerId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("user", db);
		result.put("orderlist", list);
		return result;
	}

}
