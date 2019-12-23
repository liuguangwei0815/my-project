package com.my.security.authrority;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * 权限provider 解决 所有的配置只能配置到brower ，为了可以将安全的认证配置分配到其他的自定扩展项目
 * @author Administrator
 *
 */
public interface AuthorizationProvider {
	
   void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizationconfig);
}
