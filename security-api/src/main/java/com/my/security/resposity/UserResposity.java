package com.my.security.resposity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.my.security.user.User;

public interface UserResposity extends JpaSpecificationExecutor<User>, CrudRepository<User, Long> {

	List<User> findByUserName(String userName);

}
