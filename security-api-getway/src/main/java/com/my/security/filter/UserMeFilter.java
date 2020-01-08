//package com.my.security.filter;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.commons.lang.StringUtils;
//import org.springframework.stereotype.Component;
//
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//import com.netflix.zuul.exception.ZuulException;
//
//import lombok.extern.slf4j.Slf4j;
//
///**
// * 处理前端 访问是否已经认证的判断 只拦截该拦截 永远都不往下执行 返回用户信息
// * @author liuwei
// * 这是逻辑原理在这里，其实sprngsecurity 和 oauth 都帮我做了这些工作
// */
//@Component
//@Slf4j
//public class UserMeFilter extends ZuulFilter{
//
//	@Override
//	public boolean shouldFilter() {
//		
//		RequestContext context = RequestContext.getCurrentContext();
//		HttpServletRequest request = context.getRequest();
//		if(StringUtils.equals(request.getRequestURI(), "/user/me")) {
//			log.info("UserMeFilter处理：前端获取用户信息{},这个链接需要校验",request.getRequestURI());
//			return true;
//		}
//		return false;
//	}
//
//	@Override
//	public Object run() throws ZuulException {
//		RequestContext context = RequestContext.getCurrentContext();
//		String username = context.getZuulRequestHeaders().get("username");
//		if(StringUtils.isNotBlank(username)) {
//			context.setResponseBody("{\"username\":"+username+"}");
//		}
//		context.getResponse().setContentType("application/json;charset=UTF-8");
//		context.setResponseStatusCode(200);
//		context.setSendZuulResponse(false);
//		return null;
//	}
//
//	@Override
//	public String filterType() {
//		return "pre";
//	}
//
//	@Override
//	public int filterOrder() {
//		return 4;
//	}
//
//}
