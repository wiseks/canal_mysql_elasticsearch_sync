package com.star.sync.elasticsearch.rabbitmq;

import javax.annotation.Resource;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender {
	
	@Autowired
    private AmqpTemplate rabbitTemplate;
     
    @Resource(name="taskQueue") //注入name="taskQueue"的Queue
    private Queue queue;
    
    public void send (String msg) {
        
        // 发送对象类型的消息
//        Event event = new Event(); //实现Serializable接口
//        event.setId(1101);
//        event.setName("printscreen event");
//        event.setCreateTimestamp(System.currentTimeMillis());
//        event.setUpdateTimestamp(System.currentTimeMillis());
         
        System.out.println(queue.getName());
        rabbitTemplate.convertAndSend(queue.getName(), msg); // 队列名称，消息
    }
	
}
