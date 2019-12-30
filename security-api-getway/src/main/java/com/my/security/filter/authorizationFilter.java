/**
 * 
 */
package com.my.security.filter;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuwei 授权过滤器
 */
@Component
@Slf4j
public class authorizationFilter extends ZuulFilter {

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {

		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = requestContext.getRequest();

		if (isNeedAuth(request)) {

			TokenInfo token = (TokenInfo) request.getAttribute("tokenInfo");
			if (token != null  && token.isActive() ) {
				if (!isHasPermission(token, request)) {
					handleError(403, requestContext);
				}

			} else {
				// 如果token 的链接 那么 这个不需要处理
				if (!StringUtils.startsWith(request.getRequestURI(), "/token")) {
					handleError(401, requestContext);
				}
			}
		}

		return null;
	}

	private boolean isNeedAuth(HttpServletRequest request) {
		// TODO 是否需要认证 通过url 进行判断 结合数据看的是否验证的链接进行判断
		return true;
	}

	private boolean isHasPermission(TokenInfo tokenInfo, HttpServletRequest request) {
		//TODO 这里做权限判断 通过tokenInfo 的信息 比如件简单的acl 权限控制
		return new Random().nextInt() % 2 == 0;
	}

	private void handleError(int status, RequestContext requestContext) {
		log.info("记录审计日志错误。。。。。通过request get 之前在AuditLong FIlter save的 人日志 这里进行更新");
		requestContext.getResponse().setContentType("application/json;charset=UTF-8");
		requestContext.setResponseStatusCode(status);
		requestContext.setResponseBody("{\"message\":\"auth fail" + status + "\"}");
		requestContext.setSendZuulResponse(false);// 这句话的意思 是终止，不会往业务流程来走了
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 3;// 审计过滤器
	}

}
