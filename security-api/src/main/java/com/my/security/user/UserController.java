package com.my.security.user;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.security.resposity.UserResposity;
import com.my.security.suport.SimpleResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
	
//	@Autowired
//	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private UserResposity userResposity;

	/**
	 * @RequestBody 接受json参数
	 * @param user
	 * @return
	 */
	@PostMapping
	public SimpleResponse create(@RequestBody User user) {
		return SimpleResponse.success(user);
	}

	@PutMapping("/{id}")
	public SimpleResponse update(@RequestBody User user) {
		return SimpleResponse.success();
	}

	@DeleteMapping("/{id}")
	public SimpleResponse delete(@PathVariable int id) {
		return SimpleResponse.success();
	}

	@GetMapping("/{id}")
	public SimpleResponse get(@PathVariable int id) {
		User user = new User();
		return SimpleResponse.success(user);
	}

	@GetMapping
	public SimpleResponse query(String userName) {
//		String sql = "select * from user where user_name = '"+userName+"'";
//		log.info("sql:{}",sql);
//		List liset = jdbcTemplate.queryForList(sql);
		return SimpleResponse.success(userResposity.findByUserName(userName));
	}

}
