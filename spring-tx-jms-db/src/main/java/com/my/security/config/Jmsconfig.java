/**
 * 
 */
package com.my.security.config;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.TransactionAwareConnectionFactoryProxy;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author liuwei
 *
 */
@Configuration
public class Jmsconfig {

	@Bean
	public ConnectionFactory connectionFactory() {
		ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://spring.tx.com:5025");
		TransactionAwareConnectionFactoryProxy poxy = new TransactionAwareConnectionFactoryProxy();
		poxy.setTargetConnectionFactory(factory);
		poxy.setSynchedLocalTransactionAllowed(true);
		return poxy;
	}
	
	/**
	 * 将jms 设置到事务中
	 * @param connectionFactory
	 * @return
	 */
	@Bean
	public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
		JmsTemplate jms = new JmsTemplate(connectionFactory);
		jms.setSessionTransacted(true);
		return jms;
	}
	
	
	
}
