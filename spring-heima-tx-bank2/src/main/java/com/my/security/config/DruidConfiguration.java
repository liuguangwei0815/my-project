package com.my.security.config;
/**
 * druid 配置.
*/

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

import io.seata.rm.datasource.DataSourceProxy;

@Configuration
@MapperScan(
		// 此处的mapper 可以有多个
		basePackages = { DruidConfiguration.MAPPER_PACKAGE }, sqlSessionFactoryRef = "baseSqlSessionTemplate")
public class DruidConfiguration {

	// mapper 的xml存放路径
	protected final static String MAPPER_XML_AREA = "classpath:mapper/*.xml";
	// mapper.java 存放路径，被@MapperScan扫描的，注入 sqlsession的
	protected final static String MAPPER_PACKAGE = "com.my.security.mapper";

	/*
	 * 注册过滤器
	 * 
	 * @return
	 */
	@Bean
	public FilterRegistrationBean<WebStatFilter> setDruidFilter() {
		FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.addUrlPatterns("/*");
		filterRegistrationBean.setFilter(new WebStatFilter());
		Map<String, String> initParameters = new HashMap<>();
		initParameters.put("exclusions", "*.js,*.html,*.gif,*.jpg,*.png,*.css,*.ico,*.jsp,/druid/*,/download/*");
		filterRegistrationBean.setInitParameters(initParameters);
		return filterRegistrationBean;
	}

	/**
	 * 注册一个StatViewServlet
	 * 
	 * @return
	 */
	@Bean
	public ServletRegistrationBean<StatViewServlet> setDruidServlet() {
		ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<>();
		servletRegistrationBean.setServlet(new StatViewServlet());
		servletRegistrationBean.addUrlMappings("/druid/*");

		Map<String, String> initParameters = new HashMap<>();
		initParameters.put("resetEnable", "false"); // 禁用HTML页面上的“Rest All”功能
		initParameters.put("allow", ""); // ip白名单（没有配置或者为空，则允许所有访问）
		initParameters.put("loginUsername", "maxwell"); // ++监控页面登录用户名
		initParameters.put("loginPassword", "pkusoft"); // ++监控页面登录用户密码
		initParameters.put("deny", ""); // ip黑名单
		// 如果某个ip同时存在，deny优先于allow
		servletRegistrationBean.setInitParameters(initParameters);
		return servletRegistrationBean;
	}

	/**
	 * 自动配置配置文件中的druid数据源
	 * 
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.ds0")
	public DruidDataSource druidDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		try {
			// 如果想使用 Druid 的sql监控则，此处需要写 stat
			dataSource.setFilters("stat");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dataSource;
	}

	/**
	 * 替换原始数据源配置为seata数据源 ,使用@primary 进行替换
	 */
	@Primary
	@Bean
	public DataSource dataSource() {
		return new DataSourceProxy(druidDataSource());
	}
	
	
	/**
	 * 注入 sqlSession
	 * 
	 * @return
	 * @throws Exception
	 */
	@Bean(name = "baseSqlSessionTemplate")
	@Primary
	public SqlSessionFactory setSqlSessionFactory() throws Exception {
		final SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource());
		// 设置mapper.xml 扫描路径
		bean.setMapperLocations(
				new PathMatchingResourcePatternResolver().getResources(DruidConfiguration.MAPPER_XML_AREA));
		return bean.getObject();
	}

}
