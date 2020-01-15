package com.my.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.Counter;

/**
 * 定义prometheus 指标bean 首先声明一个count 第二部为这个count赋值 （定义一个拦截器） 第三部将这个拦截器配置起来
 * 
 * @author liuwei counter 增不减 gauge 可增可减HisTOMetirToGrame：统计时间段的数据
 *         Summary：统计时间段的比例
 */
@Configuration
public class MyPrometheusConfig {

	@Autowired
	private PrometheusMeterRegistry prometheusMeterRegistry;

	/**
	 * 定义一个counter 用来计算请求数的
	 * 
	 * @return
	 */
	@Bean
	public Counter requestCount() {
		//is_request_cont 这个是自定义 "server", "method", "status" 都是自定义
		return Counter.build("is_request_count", "统计").labelNames("server", "method", "code")
				.register(prometheusMeterRegistry.getPrometheusRegistry());
	}

}
