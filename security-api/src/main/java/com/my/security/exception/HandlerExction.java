package com.my.security.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 总的捕获异常
 * @author liuwei
 *
 */
@RestControllerAdvice
public class HandlerExction {
	
	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String,Object> handle(Exception e){
		Map<String,Object> ex = new HashMap<String, Object>();
		ex.put("time",new Date().getTime());
		ex.put("message", e.getMessage());
		return ex;
	}

}
