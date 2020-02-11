package com.my.security.ioc.getbean;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
/**
 * InitializingBean 所有配置都执行赋值完了 在执行这个
 * @author liuwei
 *
 */
@Slf4j
@Component
public class InitizingBeanCoe implements InitializingBean{
	
	
	@Value("${initBean.id:1}")
	private String id;
	@Value("${initBean.name:jack}")
	private String name;


	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("第二 执行InitializingBean");
	}

}
