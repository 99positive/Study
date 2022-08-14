package com.lqb.simple.routing;

import com.lqb.Constant;
import com.lqb.simple.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Date 2022/8/14 20:30
 * @Create by lqb
 */
public class Consumer2 {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(Constant.EXCHANGE_ROUT, Constant.TYPE_DIRECT);

        String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue, Constant.EXCHANGE_ROUT, Constant.KEY_INFO);
        channel.queueBind(queue, Constant.EXCHANGE_ROUT, Constant.KEY_SUCCESS);
        channel.queueBind(queue, Constant.EXCHANGE_ROUT, Constant.KEY_WARING);

        channel.basicConsume(queue, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(this.getClass().getName() + " | " + new String(body));
            }
        });
    }
}
