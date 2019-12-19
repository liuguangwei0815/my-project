package com.my.security.code.reposity;

import java.util.concurrent.TimeUnit;

import javax.validation.ValidationException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.my.security.vaidata.code.ValidataCode;
import com.my.security.vaidata.code.processor.SessionResposityStrategy;
/**
 * app redis 保存 session
 */
@Component
public class AppRedisSessionReposityStategy implements SessionResposityStrategy{
	
	
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	

	@Override
	public void save(ServletWebRequest request, ValidataCode code, String codeType) {
		redisTemplate.opsForValue().set(buildKey(request,codeType), code, 30, TimeUnit.MINUTES);
	}

	private Object buildKey(ServletWebRequest request, String codeType) {
		String deviceId = request.getHeader("deviceId");
		if(StringUtils.isBlank(deviceId)) {
			throw new ValidationException("请在请求头上加上deviceId");
		}
		return "code:"+codeType.toLowerCase()+":"+deviceId;
	}

	@Override
	public ValidataCode get(ServletWebRequest request, String codeType) {
		Object obj = redisTemplate.opsForValue().get(buildKey(request,codeType));
		if(obj==null) {
			return null;
		}
		return (ValidataCode) redisTemplate.opsForValue().get(buildKey(request,codeType));
	}

	@Override
	public void remove(ServletWebRequest request, String codeType) {
		redisTemplate.delete(buildKey(request,codeType));
	}

}
