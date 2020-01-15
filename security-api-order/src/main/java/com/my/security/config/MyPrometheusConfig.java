package com.my.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.Summary;

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
		// is_request_cont 这个是自定义 "server", "method", "status" 都是自定义
		return Counter.build("is_request_count", "统计请求数").labelNames("server", "method", "code")
				.register(prometheusMeterRegistry.getPrometheusRegistry());
	}

	/**
	 * 统计服务的响应时间
	 */
	@Bean
	public Summary requestLacenty() {
		return Summary.build("is_request_lacenty", "统计服务请求时间").labelNames("server", "method", "code")
				.quantile(0.5, 0.05)//   这个就是我恩的TP50 看你设置多少，50%的请问时间 , 第一个参数 就是50的请求，0。05 就是误差:服务器50%的响应时间
				.quantile(0.90,0.001)// 这个就是我恩的TP90 ,线上可以设置为99 看你设置多少，50%的请问时间 , 第一个参数 就是50的请求，0。05 就是误差 ：服务器90%的响应时间
				.register(prometheusMeterRegistry.getPrometheusRegistry());
	}

}
