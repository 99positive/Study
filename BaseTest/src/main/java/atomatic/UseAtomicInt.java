package atomatic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lqb
 * on 2019/5/22.
 */
public class UseAtomicInt {

    static AtomicInteger ai = new AtomicInteger(10);

    public static void main(String[] args) {
        System.out.println(ai.getAndIncrement());

        System.out.println(ai.incrementAndGet());

        System.out.println(ai.addAndGet(60));

        System.out.println(ai.compareAndSet(72, 40));

        System.out.println(ai.get());
    }
}
