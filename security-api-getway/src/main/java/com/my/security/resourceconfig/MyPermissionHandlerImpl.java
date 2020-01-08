/**
 * 
 */
package com.my.security.resourceconfig;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuwei
 * 
 */
@Service
@Slf4j
public class MyPermissionHandlerImpl implements MyPermissionHandler {

	@Override
	public boolean isHasPermision(HttpServletRequest request, Authentication authentication) {
		log.info("网关授权控制处理器中心，当前链接：{}，当前用户信息:{}", request.getRequestURI(),
				ReflectionToStringBuilder.reflectionToString(authentication));
		return new Random().nextInt() % 2 == 0;
	}

}
