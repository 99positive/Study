package com.lqb.fanout;

import com.lqb.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Date 2022/8/13 23:47
 * @Create by lqb
 */
public class Consumer2 {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare("logs", "fanout");

        String queue = channel.queueDeclare().getQueue();

        channel.queueBind(queue, "logs", "");

        channel.basicConsume(queue, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(this.getClass().getName() + new String(body));
            }
        });
    }
}
