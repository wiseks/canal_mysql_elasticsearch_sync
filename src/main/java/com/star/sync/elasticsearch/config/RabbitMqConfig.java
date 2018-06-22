package com.star.sync.elasticsearch.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqConfig {
	
	@Value("${spring.rabbitmq.username}")
	private String username;
	
	@Value("${spring.rabbitmq.password}")
	private String password;
	
	@Value("${spring.rabbitmq.host}")
	private String host;
	
	@Value("${spring.rabbitmq.port}")
	private int port;
	
//	@Value("${rabbit.channelMax}")
//	private int channelMax;

	@Value("${spring.rabbitmq.queuename}")
	public String queuename;
	
	@Bean(name="taskQueue")
    public Queue queue() {
        return new Queue(queuename);
    }

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getQueuename() {
		return queuename;
	}
	
	
}
