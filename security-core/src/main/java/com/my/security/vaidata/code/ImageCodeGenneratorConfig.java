package com.my.security.vaidata.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.my.security.properites.SecurityProperties;

/**
 * 生成器配置 
 * @author liuwei
 *
 */
@Configuration
public class ImageCodeGenneratorConfig {
	
	@Autowired
	private  SecurityProperties securityProperties;
	
	@Bean
	@ConditionalOnMissingBean(name = "imageCodeGennerator")
	public ImageCodeGennerator imageCodeGennerator() {
		ImageCodeGenneratorImpl icg = new ImageCodeGenneratorImpl();
		icg.setSecurityProperties(securityProperties);
		return icg;
	}
	

}
