<<<<<<< HEAD
package com.my.security.listenner;
//package com.my.security.filter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.commons.pool2.impl.AbandonedConfig;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.stereotype.Component;
//
//import com.alibaba.csp.sentinel.slots.block.RuleConstant;
//import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
//import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
//import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
//import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
//
//import lombok.extern.slf4j.Slf4j;
//
///**
// * 流控
// * @author liuwei
// * ContextRefreshedEvent 这个监听器会在项目启动完之后进行 所有的bean 都加载完成之后进行，监听
// */
//@Component
//@Slf4j
//public class SentinelListinner implements ApplicationListener<ContextRefreshedEvent>{
//
//	@Override
//	public void onApplicationEvent(ContextRefreshedEvent event) {
//		log.info("sentinel 在spirng启动和bean都加载完成之后进行监听。。。");
//		FlowRule rule = new FlowRule();
//		rule.setResource("getUser");//就是你代码声明的资源
//		rule.setGrade(RuleConstant.FLOW_GRADE_QPS);//设置等级 为 qps 每秒进入的请求
//		rule.setCount(10);//每秒只能进入10个请求
//		List<FlowRule> rules = new ArrayList<FlowRule>();
//		rules.add(rule);
//		FlowRuleManager.loadRules(rules);
//		
//		
//		/**
//		 * 降级
//		 * 当每秒5个qps 才会生效 降级规则
//		 * cout 单位是毫秒 ，心跳周期时间是秒 单位是秒
//		 * RuleConstant.DEGRADE_GRADE_RT: 服务器的响应时间 ，单位是毫秒
//		 * RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT： 精确时间 就是服务器报错或者超时次数达到了一个数量 打开熔断，单位是一分钟
//		 * RuleConstant.DEGRADE_GRADE_EXCEPTION_RATIO : 比例， 一定时间内 单位是秒 比如一定时间内，有100个请求，那么你这里配资0 -1 ，你这里配置了0.3 那么一旦有30个请求失败了，那么打开熔断
//		 * 
//		 */
//		DegradeRule drule = new DegradeRule();
//		drule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
//		drule.setCount(10);//如果相应时间超过了10毫秒 打开熔断 单位是毫秒
//		drule.setTimeWindow(10);//心跳周期时间，单位是秒 ，10秒进行一次心跳
//		drule.setResource("getUser");//针对这个资源
//		//drule.setLimitApp("这和个里可以指定某个应用");
//		List<DegradeRule> druleist = new ArrayList<DegradeRule>();
//		druleist.add(drule);
//		DegradeRuleManager.loadRules(druleist);
//		
//		
//	}
//
//}
=======
package com.my.security.filter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.AbandonedConfig;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import lombok.extern.slf4j.Slf4j;

/**
 * 流控
 * @author liuwei
 * ContextRefreshedEvent 这个监听器会在项目启动完之后进行 所有的bean 都加载完成之后进行，监听
 */
@Component
@Slf4j
public class SentinelListinner implements ApplicationListener<ContextRefreshedEvent>{

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		log.info("sentinel 在spirng启动和bean都加载完成之后进行监听。。。");
		FlowRule rule = new FlowRule();
		rule.setResource("getUser");//就是你代码声明的资源
		rule.setGrade(RuleConstant.FLOW_GRADE_QPS);//设置等级 为 qps 每秒进入的请求
		rule.setCount(10);//每秒只能进入10个请求
		List<FlowRule> rules = new ArrayList<FlowRule>();
		rules.add(rule);
		FlowRuleManager.loadRules(rules);
		
		
		/**
		 * 降级
		 * 当每秒5个qps 才会生效 降级规则
		 * cout 单位是毫秒 ，心跳周期时间是秒 单位是秒
		 * RuleConstant.DEGRADE_GRADE_RT: 服务器的响应时间 ，单位是毫秒
		 * RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT： 精确时间 就是服务器报错或者超时次数达到了一个数量 打开熔断，单位是一分钟
		 * RuleConstant.DEGRADE_GRADE_EXCEPTION_RATIO : 比例， 一定时间内 单位是秒 比如一定时间内，有100个请求，那么你这里配资0 -1 ，你这里配置了0.3 那么一旦有30个请求失败了，那么打开熔断
		 * 
		 */
		DegradeRule drule = new DegradeRule();
		drule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
		drule.setCount(10);//如果相应时间超过了10毫秒 打开熔断 单位是毫秒
		drule.setTimeWindow(10);//心跳周期时间，单位是秒 ，10秒进行一次心跳
		drule.setResource("getUser");//针对这个资源
		//drule.setLimitApp("这和个里可以指定某个应用");
		List<DegradeRule> druleist = new ArrayList<DegradeRule>();
		druleist.add(drule);
		DegradeRuleManager.loadRules(druleist);
		
		
	}

}
>>>>>>> branch 'master' of https://github.com/liuwei0815/my-security-oauth2.git
