package com.lqb.simple.fanout;

import com.lqb.simple.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Date 2022/8/13 23:43
 * @Create by lqb
 */
public class Provider {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();

        Channel channel = connection.createChannel();
        //绑定交换机
        channel.exchangeDeclare("logs", "fanout");
        //push 消息， 1.交换机 2.队列 3.属性 4.值
        channel.basicPublish("logs", "", null, "logs test 111".getBytes(StandardCharsets.UTF_8));

        RabbitMQUtils.closeConnection(connection, channel);
    }

}
