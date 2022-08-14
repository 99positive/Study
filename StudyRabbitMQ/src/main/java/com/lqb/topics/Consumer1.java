package com.lqb.topics;

import com.lqb.Constant;
import com.lqb.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Date 2022/8/14 21:07
 * @Create by lqb
 */
public class Consumer1 {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(Constant.EXCHANGE_TOPICS, Constant.TYPE_TOPIC);

        String queue = channel.queueDeclare().getQueue();
        String rout_key = "user.*";

        channel.queueBind(queue, Constant.EXCHANGE_TOPICS, rout_key);

        channel.basicConsume(queue, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(this.getClass().getName() + "---" + new String(body));
            }
        });
    }
}
