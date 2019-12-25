package com.my.security.user.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
	
	
	private Long id;

	@NotBlank(message = "userName 不允许为空")
	private String userName;

	private String name;

	private String password;

}
