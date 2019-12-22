/**
 * 
 */
package com.my.security.properites;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * 
 * 这个配置包含了其他的配置 总配置文件
 * @author Administrator
 *
 */
@Data
@ConfigurationProperties("self.security")
public class SecurityProperties {
	
	
	private BrowerPerperties browser = new BrowerPerperties();
	private CodeProperties code = new CodeProperties();
	private SocailPropertis socail = new SocailPropertis();
	private Oauth2Properties oauth2  = new Oauth2Properties();
	


}
