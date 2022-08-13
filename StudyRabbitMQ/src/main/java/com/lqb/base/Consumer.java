package com.lqb.base;

import com.lqb.utils.RabbitMQUtils;
import com.rabbitmq.client.*;
import com.sun.org.apache.bcel.internal.generic.FADD;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Date 2022/8/10 23:51
 * @Create by lqb
 */
public class Consumer {

    @Test
    public void getData() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.109.131");
        factory.setPort(5672);
        factory.setVirtualHost("/vs");
        factory.setUsername("test");
        factory.setPassword("test");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("hello",  false, false, false, null);
        // 消费队列
        //1 队列 2 开启消息自动确认 3 消费消息时的回调接口
        channel.basicConsume("hello", true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body));
            }
        });

        channel.close();
        connection.close();
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("Good",  true, false, true, null);
        // 消费队列
        //1 队列 2 开启消息自动确认 3 消费消息时的回调接口
        channel.basicConsume("Good", true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body));
            }
        });

    }
}
