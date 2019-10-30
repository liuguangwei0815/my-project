package com.my.security.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 异常处理
 * @author Administrator
 *
 */
@ControllerAdvice
public class ExcetionHandlController {

	
	@ExceptionHandler(value = UserNoExistException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String,Object> HandlerUserNoExistException(UserNoExistException ex){
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("id", ex.getId());
		result.put("msg", ex.getMessage());
		return result;
	}
	
}
