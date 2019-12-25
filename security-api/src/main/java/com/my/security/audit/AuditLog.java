package com.my.security.audit;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.Modifying;

import lombok.Data;

@Entity
@Data
@EntityListeners(value = AuditingEntityListener.class)//总开关之后 开启实体监听
public class AuditLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String method;

	private String path;

	private int status;

	@CreatedBy
	private String userName;//这个也有注解 太会在记录的时候判断是否有userName 如果没有他会直接会找到Aware<你注解的名称>

	@Temporal(TemporalType.TIMESTAMP) // 自动转换为时间撮
	@CreatedDate // 会自动添加创建时间到数据库
	private Date createTime;

	@Temporal(TemporalType.TIMESTAMP) // 自动转换为时间撮
	@LastModifiedDate
	private Date modifyTime;

}
