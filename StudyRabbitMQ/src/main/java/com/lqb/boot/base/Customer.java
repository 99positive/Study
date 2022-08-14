package com.lqb.boot.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Date 2022/8/14 22:26
 * @Create by lqb
 */
@Component
@RabbitListener(queuesToDeclare = @Queue("boottest"))
@Slf4j
public class Customer {

    @RabbitHandler
    public void receive(String message) {
        log.info("receive:" + message);
    }
}
