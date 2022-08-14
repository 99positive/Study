package com.lqb.boot.topic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Date 2022/8/14 23:04
 * @Create by lqb
 */
@Component
@Slf4j
public class TopicCustomer {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(value = "topics", type = "topic"),
            key = {"user.*", "goods.#"}
    ))
    public void receive1(String msg) {
        log.info("routing receive1:{}", msg);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(value = "topics", type = "topic"),
            key = {"user.name", "goods.*"}
    ))
    public void receive2(String msg) {
        log.info("routing receive2:{}", msg);
    }

}
