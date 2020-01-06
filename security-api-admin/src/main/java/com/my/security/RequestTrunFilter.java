/**
 * 
 */
package com.my.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * @author liuwei 该过滤器都会直接过来配置文件的rutes 配置， zuul可以将所有请求都token信息带着请求头 
 */
@Component
public class RequestTrunFilter extends ZuulFilter {

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = requestContext.getRequest();
		TokenInfo token = (TokenInfo) request.getSession().getAttribute("tokenInfo");
		//如果不为空 放到请求头去
		if (token != null) {
			requestContext.addZuulRequestHeader("Authorization", "bearer "+token.getAccess_token());
		}
		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

}
