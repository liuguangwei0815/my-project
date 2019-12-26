package com.my.security.user.service;

import com.my.security.suport.SimpleResponse;
import com.my.security.user.dto.UserInfo;
import com.my.security.user.entity.User;

public interface UserService {

	SimpleResponse create(UserInfo user);

	SimpleResponse update(UserInfo user);

	SimpleResponse delete(Long id);

	SimpleResponse get(Long id);

	SimpleResponse query(String userName);

	User login(String userName);


	
}
