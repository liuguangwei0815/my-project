package com.my.security.vaidata.code;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import com.my.security.properites.SecurityProperties;
import com.my.security.vaidata.code.enums.CodeValidataType;
import com.my.security.vaidata.code.processor.ValidataProcessorHolder;

import lombok.extern.slf4j.Slf4j;

/**
 * SMS 和  Image ValidataFIlter  代码优化
 * InitializingBean 就是所有配置都已经读取完毕并且赋值之后进行执行
 * 短信验证和图片校验 放在过滤器前面
 * @author Administrator
 *
 */
@Slf4j
@Component("smsAndImageValidataFilter")
public class SmsAndImageValidataFilter extends OncePerRequestFilter implements InitializingBean {

	SessionStrategy strategy = new HttpSessionSessionStrategy();

    @Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
	private SecurityProperties securityProperties;

	PathMatcher pathMatcher = new AntPathMatcher();
	
	Map<String,CodeValidataType> urlMap = new  HashMap<>();
	
	@Autowired
	private ValidataProcessorHolder validataProcessorHolder;
	

	/**
	 * 就是所有配置都已经读取完毕并且赋值之后进行执行
	 */
	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();
		
		//将需要验证图形码和SMS需要的url 添加到Map中
		urlMap.put(SecurityContant.AUTHENTICATION_FORM, CodeValidataType.IMAGE);
		String[] iamgeconfigUrl = StringUtils
				.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getImage().getUrl(), ",");
		if (iamgeconfigUrl != null && iamgeconfigUrl.length != 0)
			Arrays.stream(iamgeconfigUrl).forEach(e -> urlMap.put(e, CodeValidataType.IMAGE));
			
		urlMap.put(SecurityContant.AUTHENTICATION_MOBILE, CodeValidataType.SMS);
		String[] smsconfigUrl = StringUtils
				.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getSmscode().getUrl(), ",");
		if (smsconfigUrl != null && smsconfigUrl.length != 0)
			Arrays.stream(smsconfigUrl).forEach(e -> urlMap.put(e, CodeValidataType.SMS));
		// 登录验证必须得图形验证码 和SMS 验证 的URL地址
		urlMap.keySet().stream().forEach(e -> log.info("加载图片验证码连接url:{}", e));
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		boolean action = urlMap.keySet().stream().filter(e -> pathMatcher.match(e, request.getRequestURI())).findFirst().isPresent();
		 log.info("是否要校验：{}，Url:{}",action, request.getRequestURI());
		// 获取当前请求连接
		if (action) {
			try {
				validataProcessorHolder.findValidataProcessorByType(urlMap.get(request.getRequestURI())).validataSmsCode(new ServletWebRequest(request, response));
			} catch (ImageCodeException e) {
				authenticationFailureHandler.onAuthenticationFailure(request, response, e);
				return;
			}
		}
		filterChain.doFilter(request, response);

	}

}
