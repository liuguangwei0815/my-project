package com.my.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.common.util.concurrent.RateLimiter;

/**
 * OncePerRequestFilter 保证每个请求只过滤一次
 * @author liuwei
 *
 */
@Component
public class RateLimiterFilter extends OncePerRequestFilter{
	
	//默认如果不写单位  单位为秒 ，下面这句话是一秒只能通过一次请求
	private RateLimiter limiter = RateLimiter.create(1);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		if(limiter.tryAcquire()) {
			filterChain.doFilter(request, response);
		}else {
			response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write("请求太多了");
			response.flushBuffer();
		}
	}

}
