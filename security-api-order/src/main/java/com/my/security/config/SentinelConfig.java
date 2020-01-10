/**
 * 
 */
package com.my.security.config;

import java.util.List;

import javax.annotation.PostConstruct;

import org.dom4j.rule.RuleManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.zookeeper.ZookeeperDataSource;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;

/**
 * @author liuwei
 *
 */
@Component
public class SentinelConfig {

	@Value("${sentinel.zookeeper.address}")
	private String zkaddress;
	@Value("${sentinel.zookeeper.path}")
	private String zkpath;
	@Value("${spring.application.name}")
	private String appName;

	@PostConstruct // 这个注解标识加载完这个类才开始
	public void initLoadRule() {

		// 告诉sentinel zookeeper的数据源
		ReadableDataSource<String, List<FlowRule>> sourlist = new ZookeeperDataSource<>(zkaddress, zkpath+"/"+appName,
				source -> JSON.parseArray(source, FlowRule.class));
		FlowRuleManager.register2Property(sourlist.getProperty());//这里只是单单是流控规则其他的需要再次写
		
		
		
		
	}

}
