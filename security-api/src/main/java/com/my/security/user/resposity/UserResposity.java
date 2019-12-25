package com.my.security.user.resposity;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.my.security.user.entity.User;

public interface UserResposity extends JpaSpecificationExecutor<User>, CrudRepository<User, Long> {

	//List<User> findByUserName(String userName);
	
	User findByUserName(String userName);

}
