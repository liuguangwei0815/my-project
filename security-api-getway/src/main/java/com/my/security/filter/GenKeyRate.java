///**
// * 
// */
//package com.my.security.filter;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.cloud.netflix.zuul.filters.Route;
//import org.springframework.stereotype.Component;
//
//import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.RateLimitUtils;
//import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.properties.RateLimitProperties;
//import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.properties.RateLimitProperties.Policy;
//import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.support.DefaultRateLimitKeyGenerator;
//
///**
// * @author liuwei
// * 如果更加细粒度的限流 比如生成key的方式，优惠券 类型的种类进行限流，这种是放在这里是不行的，这种细粒度的，网关要控制服务的性能的，
// * 可以自定义生成key  这是逻辑原理在这里，其实sprngsecurity 和 oauth 都帮我做了这些工作
// */
//@Component
//public class GenKeyRate extends DefaultRateLimitKeyGenerator{
//
//	public GenKeyRate(RateLimitProperties properties, RateLimitUtils rateLimitUtils) {
//		super(properties, rateLimitUtils);
//	}
//
//	@Override
//	public String key(HttpServletRequest request, Route route, Policy policy) {
//		return super.key(request, route, policy);
//	}
//	
//	
//
//}
