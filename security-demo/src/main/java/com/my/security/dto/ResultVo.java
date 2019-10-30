package com.my.security.dto;

import java.io.Serializable;

import org.springframework.validation.FieldError;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Slf4j
public class ResultVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4305902538446906959L;
	private int code;
	private String msg;
	private Object data;

	public static ResultVo retSucc(Object data) {
		return new ResultVo(1, "操作成功", data);
	}

	public static ResultVo retSucc() {
		return new ResultVo(1, "操作成功", null);
	}

	public static ResultVo retFail() {
		return new ResultVo(0, "操作失败", null);
	}

	public static ResultVo retFailMsg(String msg) {
		log.info("receive msg:"+msg);
		return new ResultVo(0, msg, null);
	}


	public static ResultVo retFileErorMsg(Object e) {
		FieldError err = (FieldError)e;
		return retFailMsg(err.getField()+":"+err.getDefaultMessage());
	}


}
