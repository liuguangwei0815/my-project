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

import com.my.security.entity.Order;

/**
 * @author liuwei
 *
 */
@Service
public class CustomerService {

	@Autowired
	@Qualifier("userJdbcTemplate")
	private JdbcTemplate userJdbcTemplate;

	@Autowired
	@Qualifier("orderJdbcTemplate")
	private JdbcTemplate orderJdbcTemplate;

	private String createOrderSql = "insert into `order` (customer_id,title,amount) VALUES (?,?,?)";
	private String upateUserAmtSql = "update customer set deposit = deposit - ? where id = ?";

	@Transactional
	public Order creaeteOrder(Order order) {
		// 更跟新用户余额
		userJdbcTemplate.update(upateUserAmtSql, order.getAmount(), order.getCustomerId());
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

		Map<String, Object> user = userJdbcTemplate.queryForMap("select * from customer where id = " + customerId);
		List<Map<String, Object>> list = orderJdbcTemplate
				.queryForList("select * from `order` where customer_id = " + customerId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("user", user);
		result.put("orderlist", list);
		return result;
	}

}
