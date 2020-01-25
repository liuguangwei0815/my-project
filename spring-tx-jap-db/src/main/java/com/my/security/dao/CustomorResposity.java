package com.my.security.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.my.security.entity.Customer;

public interface CustomorResposity extends JpaSpecificationExecutor<Customer> , CrudRepository<Customer, Long>{



}
