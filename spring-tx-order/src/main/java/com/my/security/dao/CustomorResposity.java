package com.my.security.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.my.security.bean.CustomerOrder;


public interface CustomorResposity extends JpaSpecificationExecutor<CustomerOrder> , CrudRepository<CustomerOrder, Long>{



}
