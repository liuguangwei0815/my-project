package com.my.security.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

import com.fasterxml.jackson.annotation.JsonView;
import com.my.security.validata.MyContraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable{
	
	public interface UserSimpleView{};
	public interface UserDetailView extends UserSimpleView{};

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonView(UserSimpleView.class)
	private int id;
	@JsonView(value = UserSimpleView.class)
	@NotBlank(message = "用户名不允许为空")
	@MyContraint(message = "这是个测试注解")
	private String username;
	@JsonView(value = UserDetailView.class)
	@NotBlank(message = "密码不允许为空")
	private String password;
	@JsonView(UserSimpleView.class)
	@Past
	private Date birthday;

}
