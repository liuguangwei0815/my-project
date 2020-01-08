package com.my.security.resourceconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

/**
 * 这个是处理表达始终的变量，需要这个表达式处理去去声明
 * 
 * @author liuwei
 *
 */
@Component
public class MyOAuth2WebSecurityExpressionHandler extends OAuth2WebSecurityExpressionHandler {
	
	@Autowired
	private MyPermissionHandler myPermissionHandler;

	@Override
	protected StandardEvaluationContext createEvaluationContextInternal(Authentication authentication,
			FilterInvocation invocation) {
		StandardEvaluationContext sc = super.createEvaluationContextInternal(authentication, invocation);
		sc.setVariable("myPermissionHandler", myPermissionHandler);//指定表达式的变量
		return sc;
	}

}
