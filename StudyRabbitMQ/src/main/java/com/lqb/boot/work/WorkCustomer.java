package com.lqb.boot.work;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Date 2022/8/14 22:49
 * @Create by lqb
 */
@Component
@Slf4j
public class WorkCustomer {

    @RabbitListener(queuesToDeclare = @Queue(value = "work"))
    public void receive1(String msg) {
        log.info("receive1 -- :" + msg);
    }

    @RabbitListener(queuesToDeclare = @Queue(value = "work"))
    public void receive2(String msg) {
        log.info("receive2 -- :" + msg);
    }
}
