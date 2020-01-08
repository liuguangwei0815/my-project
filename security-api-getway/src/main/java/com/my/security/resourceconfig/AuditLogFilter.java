package com.my.security.resourceconfig;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

/**
 * 审计过滤器
 * 
 * @author liuwei
 *
 */
@Slf4j
//这里不能添加component 因为添加了 spring 看到你集成了filter 默认会添加到sprng的过滤器中，因为你要控制顺序，所以你不能直接声明为组件 需要你自己手动配置
public class AuditLogFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("add 1 log user:{}", username);
		filterChain.doFilter(request, response);
		// 在异常处理做标识
		if (StringUtils.isBlank((String) request.getAttribute("hasuplogstatus"))) {
			log.info("3 update log success");
		}

	}

}
