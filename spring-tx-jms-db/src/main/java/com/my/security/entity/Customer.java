/**
 * 
 */
package com.my.security.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

/**
 * @author liuwei
 *
 */
@Data
@Entity(name = "customer")
@EntityListeners(value = AuditingEntityListener.class)//总开关之后 开启实体监听
public class Customer {

	@Id
	@GeneratedValue
	private Long id;
	private String username;
	private BigDecimal deposit;// 余额

}
