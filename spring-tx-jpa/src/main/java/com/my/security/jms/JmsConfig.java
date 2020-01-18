//package com.my.security.jms;
//
//import javax.jms.ConnectionFactory;
//
//import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jms.config.DefaultJcaListenerContainerFactory;
//import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
//import org.springframework.jms.config.JmsListenerContainerFactory;
//import org.springframework.jms.connection.JmsTransactionManager;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.transaction.PlatformTransactionManager;
//
///**
// * 将jms事务将原始的session管理 移交给jmsTrasactionManger 管理
// * 
// * @author liuwei
// *
// */
//@Configuration
//public class JmsConfig {
//
//	/**
//	 * 定义一个jmsTractionManger
//	 * 
//	 * @param connectionFactory
//	 * @return
//	 */
//	@Bean
//	public PlatformTransactionManager jmsTransactionManager(ConnectionFactory connectionFactory) {
//		return new JmsTransactionManager(connectionFactory);
//	}
//
//	/**
//	 * 设置jms 发送和接受
//	 * @param connectionFactory
//	 * @return
//	 */
//	@Bean
//	public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
//		return new JmsTemplate(connectionFactory);
//	}
//
//	/**
//	 * 设置监听器工厂 工厂配置链接 和 事务管理器
//	 * @param config
//	 * @param jmt
//	 * @param connectionFactory
//	 * @return
//	 */
//	@Bean
//	public JmsListenerContainerFactory<?> jmsFactory(DefaultJmsListenerContainerFactoryConfigurer config,
//			PlatformTransactionManager jmt, ConnectionFactory connectionFactory) {
//		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//		factory.setTransactionManager(jmt);
//		factory.setReceiveTimeout(100000l);// 设置接收超时时间 不然会出现等待
//		factory.setConnectionFactory(connectionFactory);//监听器 
//		return factory;
//	}
//
//}
