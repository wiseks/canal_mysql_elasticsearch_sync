package com.star.sync.elasticsearch.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

	
	@RabbitListener(queues = "account") // //监听器监听指定的Queue
    public void receive(String msg) {
        System.out.println("receiver: " + msg);
    }
}
