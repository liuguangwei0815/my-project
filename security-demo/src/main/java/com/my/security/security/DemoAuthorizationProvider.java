package com.my.security.security;

import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import com.my.security.authrority.AuthorizationProvider;

/**
 * 将之前只能配置到browser 包的 权限配置给挪出来 可以增加方便的的给每个链接给权限
 * @author Administrator
 *
 */
@Component
@Order(Integer.MAX_VALUE)//因为anyRequest 这个需要 放到最后的 是有顺序的 所以需要加上这个 ，在managerProvider 中 回去遍历  会通过这个排序进行顺序实例化
public class DemoAuthorizationProvider implements AuthorizationProvider{

	@Override
	public void config(
			ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizationconfig) {
		
		//anyRequest  一般需要放到最后
		//authorizationconfig.antMatchers("/user/*").hasAuthority("query");
		authorizationconfig.anyRequest().access("@rbacService.isHashPermission(request,authentication)");
	}

}
