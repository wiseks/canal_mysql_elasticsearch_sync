package com.star.sync.elasticsearch.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class NewTask {
	private final static String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("47.98.169.15");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //队列持久化，在RabbitMQ重启保证队列不会丢失
        boolean durable = true;
        channel.queueDeclare(TASK_QUEUE_NAME, durable, false, false, null);



        //发送10条消息，依次在消息后面附加1-10个点  
        for (int i = 10; i > 0; i--)  
        {  
            String dots = "";  
            for (int j = 0; j < i; j++)  
            {  
                dots += ".";  
            }  
            String message = "helloworld" + dots+dots.length();  
            //MessageProperties.PERSISTENT_TEXT_PLAIN消息持久化，保证服务重启后消息不会丢失
            channel.basicPublish("", TASK_QUEUE_NAME, 
                    MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());  
            System.out.println(" [x] Sent '" + message + "'");
        }  

        channel.close();
        connection.close();
    }
}
