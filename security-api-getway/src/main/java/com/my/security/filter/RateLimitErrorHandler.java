///**
// * 
// */
//package com.my.security.filter;
//
//import java.io.IOException;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//
//import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.repository.DefaultRateLimiterErrorHandler;
//import com.netflix.zuul.context.RequestContext;
//
///**
// * @author liuwei 可以自定义返回错误 这是逻辑原理在这里，其实sprngsecurity 和 oauth 都帮我做了这些工作
// */
//@Component
//public class RateLimitErrorHandler extends DefaultRateLimiterErrorHandler {
//	@Override
//	public void handleError(String msg, Exception e) {
//		super.handleError(msg, e);
//		RequestContext requestContext = RequestContext.getCurrentContext();
////		requestContext.getResponse().setContentType("application/json;charset=utf-8");
////		requestContext.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
////		try {
////			requestContext.getResponse().getWriter().write("zuul 异常");
////			requestContext.getResponse().flushBuffer();
////		} catch (IOException e1) {
////			e1.printStackTrace();
////		}
//		requestContext.setSendZuulResponse(false);
//	}
//}
