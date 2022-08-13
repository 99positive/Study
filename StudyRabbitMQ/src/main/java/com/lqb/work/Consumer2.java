package com.lqb.work;

import com.lqb.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Arrays;

/**
 * @Date 2022/8/12 23:54
 * @Create by lqb
 */
public class Consumer2 {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("WorkQueue", true, false, false, null);
        channel.basicQos(1);
        channel.basicConsume("WorkQueue", false, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    Thread.sleep(2000);
                    System.out.println(new String(body));
                    channel.basicAck(envelope.getDeliveryTag(), false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
