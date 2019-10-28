package com.my.security.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonView;

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
	private String userName;
	@JsonView(value = UserDetailView.class)
	private String passWord;
	

}
