package com.my.security.suport;

import org.apache.commons.lang.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimpleResponse {

	private String code;
	private String msg;
	private Object data;

	public static SimpleResponse success(String msg, Object data) {
		return new SimpleResponse("1", msg, data);
	}

	public static SimpleResponse success(Object data) {
		return new SimpleResponse("1", "操作成功", data);
	}

	public static SimpleResponse success() {
		return new SimpleResponse("1", "操作成功", null);
	}

	public static SimpleResponse fail(String msg) {
		return new SimpleResponse("0", msg, null);
	}

	public static SimpleResponse fail() {
		return new SimpleResponse("0", "操作失败", null);
	}
	
	public boolean isSucc() {
		return StringUtils.equals(this.getCode(), "1");
	}
	

}
