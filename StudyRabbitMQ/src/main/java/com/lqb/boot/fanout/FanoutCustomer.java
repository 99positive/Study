package com.lqb.boot.fanout;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Date 2022/8/14 23:02
 * @Create by lqb
 */
@Component
@Slf4j
public class FanoutCustomer {

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(name = "logs", type = "fanout")
            )
    })
    public void receive1(String msg) {
        log.info("fanout receive1:" +msg);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(name = "logs", type = "fanout")
            )
    })
    public void receive2(String msg) {
        log.info("fanout receive2:" +msg);
    }
}
