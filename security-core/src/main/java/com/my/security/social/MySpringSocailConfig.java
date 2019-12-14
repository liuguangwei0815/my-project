package com.my.security.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * socal 默认拦截地址是/auth 想要改变这个 需要覆盖postProcess 方法 设置你想要的路径
 * @author Administrator
 *
 */
public class MySpringSocailConfig extends SpringSocialConfigurer {

	String filterProcessesUrl;//socail登录拦截的url 
	
	public MySpringSocailConfig(String filterProcessesUrl) {
		this.filterProcessesUrl = filterProcessesUrl;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T postProcess(T object) {
		SocialAuthenticationFilter filter = (SocialAuthenticationFilter)object;
		filter.setFilterProcessesUrl(filterProcessesUrl);
		return (T) filter;
	}

	
	
}
