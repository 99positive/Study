package com.lqb.boot.routing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Date 2022/8/14 23:25
 * @Create by lqb
 */
@Component
@Slf4j
public class RoutingCustomer {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(value = "routings", type = "direct"),
            key = {"rout", "error"}
    ))
    public void receive1(String msg) {
        log.info("routing receive1:{}", msg);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(value = "routings", type = "direct"),
            key = {"error"}
    ))
    public void receive2(String msg) {
        log.info("routing receive2:{}", msg);
    }
}
