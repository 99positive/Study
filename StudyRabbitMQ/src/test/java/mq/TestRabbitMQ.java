package mq;

import com.lqb.RabbitApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Date 2022/8/14 22:23
 * @Create by lqb
 */
@SpringBootTest(classes = RabbitApplication.class)
@RunWith(SpringRunner.class)
public class TestRabbitMQ {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testTopic(){
        rabbitTemplate.convertAndSend("topics", "user.test", "haha");
        rabbitTemplate.convertAndSend("topics", "goods.name", "jokes");
        rabbitTemplate.convertAndSend("topics", "user.name", "computer");
        rabbitTemplate.convertAndSend("topics", "user.age", "19");
    }

    @Test
    public void testRouting(){
        rabbitTemplate.convertAndSend("routings", "rout", "routing -- test11");
        rabbitTemplate.convertAndSend("routings", "error", "routing -- test22");
    }

    @Test
    public void testFanout(){
        rabbitTemplate.convertAndSend("logs", "", "fanout type~~");
    }

    @Test
    public void testWork(){
        for (int i = 0; i < 10; i++) {
            System.out.println("work model >> " + i);
            rabbitTemplate.convertAndSend("work", "work model >> " + i);
        }
    }

    @Test
    public void test() {
        rabbitTemplate.convertAndSend("boottest", "start_01");
    }
}
