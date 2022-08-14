package com.lqb.simple.routing;

import com.lqb.Constant;
import com.lqb.simple.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Date 2022/8/14 0:00
 * @Create by lqb
 */
public class Provider {

    public static void main(String[] args) throws IOException {

        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(Constant.EXCHANGE_ROUT, Constant.TYPE_DIRECT);

        channel.basicPublish(Constant.EXCHANGE_ROUT, Constant.KEY_INFO, null, (Constant.KEY_INFO + "__GO").getBytes(StandardCharsets.UTF_8));
        channel.basicPublish(Constant.EXCHANGE_ROUT, Constant.KEY_WARING, null, (Constant.KEY_WARING + "__GO").getBytes(StandardCharsets.UTF_8));
        channel.basicPublish(Constant.EXCHANGE_ROUT, Constant.KEY_SUCCESS, null, (Constant.KEY_SUCCESS + "__GO").getBytes(StandardCharsets.UTF_8));
        channel.basicPublish(Constant.EXCHANGE_ROUT, Constant.KEY_ERROR, null, (Constant.KEY_ERROR + "__GO").getBytes(StandardCharsets.UTF_8));

        RabbitMQUtils.closeConnection(connection, channel);
    }
}
