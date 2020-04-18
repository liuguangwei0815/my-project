/**
 * 
 */
package com.my.security.resourceconfig;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.http.AccessTokenRequiredException;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuwei
 * 权限控制
 */
@Service
@Slf4j
public class MyPermissionHandlerImpl implements MyPermissionHandler {

	@Override
	public boolean isHasPermision(HttpServletRequest request, Authentication authentication) {
		log.info("网关授权控制处理器中心，当前链接：{}，当前用户信息:{}", request.getRequestURI(),
				ReflectionToStringBuilder.reflectionToString(authentication));
		//需要权限认证，如果不给过那么不给过
		if(authentication instanceof AnonymousAuthenticationToken) {
			throw new AccessTokenRequiredException(null);
		}
		//下面这个模拟403
		//return new Random().nextInt() % 2 == 0;
		return true;
	}

}
