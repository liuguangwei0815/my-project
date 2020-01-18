package com.my.security.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
@Entity
@Data
@EntityListeners(value = AuditingEntityListener.class)//总开关之后 开启实体监听
public class Customer {

	@Id
	@GeneratedValue
	private Long id;
	@Column(name = "user_name",unique = true)
	private String username;
	private String password;
	private String role;

}
