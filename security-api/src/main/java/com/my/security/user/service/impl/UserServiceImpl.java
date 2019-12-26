/**
 * 
 */
package com.my.security.user.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lambdaworks.crypto.SCryptUtil;
import com.my.security.suport.SimpleResponse;
import com.my.security.user.dto.UserInfo;
import com.my.security.user.entity.User;
import com.my.security.user.resposity.UserResposity;
import com.my.security.user.service.UserService;

/**
 * @author liuwei
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserResposity userResposity;

	/**
	 * 接受json参数
	 * 
	 * @param user
	 * @return
	 */
	@Override
	public SimpleResponse create(UserInfo user) {
		User u = new User();
		user.setPassword(SCryptUtil.scrypt(user.getPassword(), 32768, 8, 1));// 32768, 8, 1) 这几个参数控制系统的生成的系统值 比如内存 为了不达到100%
		BeanUtils.copyProperties(user, u);
		userResposity.save(u);
		user.setId(u.getId());
		return SimpleResponse.success(user);
	}
	@Override
	public SimpleResponse update(UserInfo user) {
		return SimpleResponse.success();
	}
	@Override
	public SimpleResponse delete(Long id) {
		return SimpleResponse.success();
	}
	@Override
	public SimpleResponse get(Long id) {
		User user = userResposity.findById(id).orElse(null);//.build();
		return SimpleResponse.success(user);
	}
	@Override
	public SimpleResponse query(String userName) {
		return SimpleResponse.success(userResposity.findByUserName(userName));
	}
	@Override
	public User login(String userName) {
		return userResposity.findByUserName(userName);
	}

}
