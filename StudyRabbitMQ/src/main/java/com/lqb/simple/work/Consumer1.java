package com.lqb.simple.work;

import com.lqb.simple.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Date 2022/8/12 23:54
 * @Create by lqb
 */
public class Consumer1 {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare("WorkQueue", true, false, false, null);
        channel.basicQos(1);
        // 关闭自动确认
        channel.basicConsume("WorkQueue", false, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}
