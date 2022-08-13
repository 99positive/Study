package com.lqb.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Date 2022/8/11 0:15
 * @Create by lqb
 */
public class RabbitMQUtils {

    private static ConnectionFactory factory;

    static {
        factory = new ConnectionFactory();
        factory.setHost("192.168.109.131");
        factory.setPort(5672);
        factory.setUsername("test");
        factory.setPassword("test");
        factory.setVirtualHost("/vs");
    }

    public static Connection getConnection() {
        try {
            return factory.newConnection();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closeConnection(Connection connection, Channel channel) {
        try {
            if (channel != null) channel.close();
            if (connection != null) connection.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
