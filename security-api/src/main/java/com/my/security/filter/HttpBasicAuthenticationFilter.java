package com.my.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.lambdaworks.crypto.SCryptUtil;
import com.my.security.user.entity.User;
import com.my.security.user.resposity.UserResposity;

import lombok.extern.slf4j.Slf4j;

/**
 * httpBasic 验证
 * 
 * @author liuwei
 *
 */
@Component
@Order(2)
@Slf4j
public class HttpBasicAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private UserResposity userResposity;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.info("认证过滤器。。。");
		String authorization = request.getHeader("Authorization");
		if (StringUtils.isNotBlank(authorization)) {
			String basic = StringUtils.substringAfter(authorization, "Basic ");
			String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(new String(Base64Utils.decodeFromString(basic)), ":");
			String userName = items[0];
			String passWord = items[1];
			User user = userResposity.findByUserName(userName);
			//if(user!=null&&StringUtils.equals(passWord, user.getPassword())) {
			if(user!=null&&SCryptUtil.check(passWord, user.getPassword())) {
				request.setAttribute("user", user);
			}
		}
		filterChain.doFilter(request, response);
	}

}
