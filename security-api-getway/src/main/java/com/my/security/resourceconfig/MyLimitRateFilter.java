/**
 * 
 */
package com.my.security.resourceconfig;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.common.util.concurrent.RateLimiter;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuwei
 * 使用guava 的流控过滤器 和 spring zuul limitRate 
 */
@Slf4j
public class MyLimitRateFilter extends OncePerRequestFilter{
	
	private RateLimiter limit = RateLimiter.create(1);//一秒钟只能通过一次请求

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		log.info("开始执行 限流过滤器");
		if(limit.tryAcquire()) {
			filterChain.doFilter(request, response);
		}else {
			response.setContentType("application/json;charset=UTF-8");
			response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
			response.getWriter().write("{\"message\":\"太多请求了\"}");
			response.flushBuffer();
		}
	}

}
