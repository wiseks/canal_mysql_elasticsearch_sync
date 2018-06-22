package com.star.sync.elasticsearch.rabbitmq;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Worker {
	private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] argv) throws Exception {
      //创建连接和通道
      ConnectionFactory factory = new ConnectionFactory();
      factory.setHost("47.98.169.15");
      final Connection connection = factory.newConnection();
      final Channel channel = connection.createChannel();

      //队列持久化，在RabbitMQ重启保证队列不会丢失
      boolean durable = true;
      channel.queueDeclare(TASK_QUEUE_NAME, durable, false, false, null);
      System.out.println(" [*] Waiting for messages. To exit press CTRL+C");


      //只有在消费者空闲的时候会发送下一条信息
      int prefetchCount = 1;
      channel.basicQos(prefetchCount);

      final Consumer consumer = new DefaultConsumer(channel) {
           @Override
           public void handleDelivery(String consumerTag, Envelope envelope,
                   AMQP.BasicProperties properties, byte[] body) throws IOException {
              String message = new String(body, "UTF-8");

              System.out.println(" [x] Received '" + message);
              try {
                    doWork(message);
                  } finally {
                    System.out.println(" [x] Done");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                  }
                }
          };
          //这里auto=false表示代开应答机制
          boolean autoAck = false;
          channel.basicConsume(TASK_QUEUE_NAME, autoAck, consumer);
    }

    /**
     * 
    * @Title: doWork
    * @Description: TODO(模拟做任务，每个'.'耗时一秒)
    * @param @param task    设定文件
    * @return void    返回类型
    * @throws
     */
    private static void doWork(String task) {
      for (char ch : task.toCharArray()) {
        if (ch == '.') {
          try {
            Thread.sleep(1000);
          } catch (InterruptedException _ignored) {
            Thread.currentThread().interrupt();
          }
        }
      }
    }
}
