package com.lqb.topics;

import com.lqb.Constant;
import com.lqb.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Date 2022/8/14 21:06
 * @Create by lqb
 */
public class Provider {

    public static void main(String[] args) throws IOException {

        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(Constant.EXCHANGE_TOPICS, Constant.TYPE_TOPIC);

        String rout_key = "user";
        channel.basicPublish(Constant.EXCHANGE_TOPICS, rout_key, null, (rout_key + " topic!!!").getBytes(StandardCharsets.UTF_8));

        RabbitMQUtils.closeConnection(connection, channel);
    }
}
