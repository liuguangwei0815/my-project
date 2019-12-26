package com.my.security.user.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.BeanUtils;

import com.my.security.user.dto.UserInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	/**
	 * 
	 */
	//private static final long serialVersionUID = -2928265419924907283L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//生成策略数据库自动生成
	private Long id;

	@NotBlank(message = "数据库层面也不允许为空")
	private String userName;

	private String name;

	private String password;
	
	private String permission;//简单的acl 权限

	public UserInfo build() {
		UserInfo user = new UserInfo();
		BeanUtils.copyProperties(this, user);
		return user;
	}

	public boolean permission(String method) {
		if(method.equalsIgnoreCase("get")&&this.getPermission().contains("r")) {
			return true;
		}else if(this.getPermission().contains("w")) {
			return true;
		}
		return false;
	}

}
