package com.lqb.simple.work;

import com.lqb.simple.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;

/**
 * @Date 2022/8/12 23:44
 * @Create by lqb
 */
public class WorkProvider {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare("WorkQueue", true, false, false, null);

        for (int i = 0; i < 30; i++) {
            channel.basicPublish("", "WorkQueue", MessageProperties.MINIMAL_PERSISTENT_BASIC, ("data_"  + i).getBytes());
        }

        RabbitMQUtils.closeConnection(connection, channel);
    }
}
