/**
 * 
 */
package com.my.security.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.swing.JApplet;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

/**
 * @author liuwei
 *
 */
@Configuration
public class DBconfig {

	@Bean
	@Primary//
	@ConfigurationProperties(prefix =  "spring.ds-user") // 然后可以通过这组配置生成DataSourceProperties
	public DataSourceProperties userDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@Primary//默认优先使用
	public DataSource userDataSource() {
		// 指定datasource类型
		return userDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}

	/*
	 * @Bean public JdbcTemplate userJdbcTemplate(@Qualifier("userDataSource")
	 * DataSource dataSource) { //return new JdbcTemplate(userDataSource()); //或者
	 * return new JdbcTemplate(dataSource); }
	 */
	
	
	@Bean
	@ConfigurationProperties(prefix = "spring.ds-order") // 然后可以通过这组配置生成DataSourceProperties ，有一点就是这个规则需要中横线
	public DataSourceProperties orderDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	public DataSource orderDataSource() {
		// 指定datasource类型
		return orderDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}

	@Bean
	public JdbcTemplate orderJdbcTemplate() {
		return new JdbcTemplate(orderDataSource());
	}
	
	
	/**
	 * jpa 设置事务链需要设置EntityManagerFactory
	 * @return
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		
		HibernateJpaVendorAdapter hadpapter = new HibernateJpaVendorAdapter();
		//不需要维护 就是自动身材ddl语句
		hadpapter.setGenerateDdl(false);
		
		LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
		bean.setJpaVendorAdapter(hadpapter);
		bean.setDataSource(userDataSource());
		bean.setPackagesToScan("com.my.security");
		return bean;
	}
	
	
	/**
	 * No bean named 'transactionManager' available: No matching PlatformTransactionManager bean found for qualifier 
	 * 'transactionManager' - neither qualifier match nor bean name match!
	 * 这个名词必须这个样子 不然会报错
	 * @return
	 */
	@Bean
	public PlatformTransactionManager transactionManager() {
		
		//PlatformTransactionManager usertxM = new DataSourceTransactionManager(userDataSource());//执行的事务都是在自己的事务管理进行提交和回滚
		
		JpaTransactionManager usertxM = new JpaTransactionManager();
		usertxM.setEntityManagerFactory(entityManagerFactory().getObject());
		
		PlatformTransactionManager ordertxM = new DataSourceTransactionManager(orderDataSource());//
		//事务链管理
		ChainedTransactionManager chain = new ChainedTransactionManager(usertxM,ordertxM);//这个顺序关系到 order先执行 user 后执行
		return  chain;
	}
	
}
