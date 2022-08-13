package com.lqb.base;

import ch.qos.logback.core.util.ContextUtil;
import com.lqb.utils.RabbitMQUtils;
import com.rabbitmq.client.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @Date 2022/8/10 23:34
 * @Create by lqb
 */
public class Provider {

    @Test
    public void addData() throws IOException, TimeoutException {
        // 创建连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 设置连接rabbit主机
        connectionFactory.setHost("192.168.109.131");
        //设置端口号
        connectionFactory.setPort(5672);
        //设置连接虚拟主机
        connectionFactory.setVirtualHost("/vs");
        //设置访问用户名和密码
        connectionFactory.setUsername("test");
        connectionFactory.setPassword("test");
        //获取连接对象，获取连接通道
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        //绑定消息队列
        //1队列 2 队列持久化 3独占 4自动删除 5额外参数
        channel.queueDeclare("hello", false, false, false, null);
        //发布消息
        //MessageProperties.MINIMAL_PERSISTENT_BASIC 消息持久化
        channel.basicPublish("", "hello", null, "test".getBytes(StandardCharsets.UTF_8));

        channel.close();
        connection.close();
    }

    @Test
    public void addData2() throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        // 2 队列持久化
        // 3 独占队列 一条通道独占。
        // 4 是否删除 消费完，并且消费者与队列连接关闭后删除
        channel.queueDeclare("Good", true, false ,true, null);
        //MessageProperties.MINIMAL_PERSISTENT_BASIC 消息持久化
        channel.basicPublish("", "Good", MessageProperties.MINIMAL_PERSISTENT_BASIC, "vv_times".getBytes(StandardCharsets.UTF_8));

        RabbitMQUtils.closeConnection(connection, channel);
    }

}
