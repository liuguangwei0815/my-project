package com.my.security.filter;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 拦截器配置
 * 拦截器需要配置
 * 
 * @author liuwei
 *
 *
 */
@Configuration
@EnableJpaAuditing//开启jpa 审计的总开管
public class InterceptorConfig extends WebMvcConfigurationSupport {

	@Autowired
	private AuditLogInterceptor auditLogInterceptor;
	@Autowired
	private AclInterceptor aclInterceptor;

	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(auditLogInterceptor);//.addPathPatterns(patterns) 这个可以添加指定链接的 针对某些请求;
		registry.addInterceptor(aclInterceptor);
	}
	
	/**
	 * 这个bean 他会提供给注解@createBy 他会直接找到这个bean 然后返回 这个bean的getCurrentAuditor的任意对戏，泛型是你注释的字段的类型
	 * @return
	 */
	@Bean
	public AuditorAware<String> auditorAware(){
		return new AuditorAware<String>() {
			@Override
			public Optional<String> getCurrentAuditor() {
				return Optional.of("jojo");
			}
		};
	}

}
