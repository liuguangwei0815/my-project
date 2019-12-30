/**
 * 
 */
package com.my.security.filter;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuwei
 * 认证filter 之后的审计过滤器
 *
 */
@Component
@Slf4j
public class AuditLogFilter extends ZuulFilter {

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		log.info(" 审计日志 insert into log");
		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 2;//在认证过滤器之后
	}

}
