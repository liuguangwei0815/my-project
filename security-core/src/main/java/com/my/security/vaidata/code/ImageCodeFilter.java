package com.my.security.vaidata.code;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import com.my.security.properites.SecurityProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * InitializingBean 就是所有配置都已经读取完毕并且赋值之后进行执行
 * 
 * @author Administrator
 *
 */
@Slf4j
public class ImageCodeFilter extends OncePerRequestFilter implements InitializingBean {

	RequestCache requestCache = new HttpSessionRequestCache();
	SessionStrategy strategy = new HttpSessionSessionStrategy();

	@Setter
	@Getter
	AuthenticationFailureHandler authenticationFailureHandler;
	@Setter
	@Getter
	SecurityProperties securityProperties;

	PathMatcher pathMatcher = new AntPathMatcher();

	Set<String> setUrl = new HashSet<String>();

	/**
	 * 就是所有配置都已经读取完毕并且赋值之后进行执行
	 */
	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();
		String[] configUrl = StringUtils
				.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getImage().getUrl(), ",");
		if (configUrl != null && configUrl.length != 0)
			Arrays.stream(configUrl).sorted().forEach(e -> setUrl.add(e));
		// 登录验证必须得图形验证码验证
		setUrl.add("/authentication/form");
		setUrl.stream().forEach(e -> log.info("加载图片验证码连接url:{}", e));
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// SavedRequest saverequest = requestCache.getRequest(request, response);
		// String url = saverequest.getRedirectUrl();
		// log.info("saveRequest get Url:{}", url);
		log.info("request get UrI:{}", request.getRequestURI());
		boolean action = Boolean.FALSE;
		action = setUrl.stream().filter(e -> pathMatcher.match(e, request.getRequestURI())).findFirst().isPresent();
		log.info("action 是否匹配上:{}", action);
		setUrl.stream().forEach(e -> log.info("e:{},是否匹配：{}", e, pathMatcher.match(e, request.getRequestURI())));
		// 获取当前请求连接
		if (action) {
			try {
				validataImageCode(request, response);
			} catch (ImageCodeException e) {
				authenticationFailureHandler.onAuthenticationFailure(request, response, e);
				return;
			}
		}
		filterChain.doFilter(request, response);

	}

	private void validataImageCode(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {

		ImageCode imageCode = (ImageCode) strategy.getAttribute(new ServletWebRequest(request),
				ImageCodeContant.SESSIONKEY);
		if (imageCode == null)
			throw new ImageCodeException("验证码不存在");

		log.info("getParameter get Code:{}", request.getParameter("imageCode"));

		String code = ServletRequestUtils.getStringParameter(request, "imageCode");

		if (StringUtils.isBlank(code))
			throw new ImageCodeException("请输入验证码");

		// 是否已过期
		if (imageCode.isExpire()) {
			strategy.removeAttribute(new ServletWebRequest(request), ImageCodeContant.SESSIONKEY);
			throw new ImageCodeException("该验证码已过期");
		}

		if (!StringUtils.equals(imageCode.getCode(), code))
			throw new ImageCodeException("请输入正确的验证码");

		strategy.removeAttribute(new ServletWebRequest(request), ImageCodeContant.SESSIONKEY);
	}

}
