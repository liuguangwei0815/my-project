package com.my.security.bean;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
@Entity(name = "customer_order")
@Data
@EntityListeners(value = AuditingEntityListener.class)//总开关之后 开启实体监听
public class CustomerOrder {

	@Id
	@GeneratedValue
	private Long id;
	private String title;
	private String details;
	private BigDecimal amount;

}
