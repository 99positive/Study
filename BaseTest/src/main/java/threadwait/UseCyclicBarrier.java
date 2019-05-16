package threadwait;

import tools.SleepTools;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by lqb
 * on 2019/5/16.
 */
public class UseCyclicBarrier {

    private static final int THREAD_NUM = 3;
    private static CyclicBarrier barrier = new CyclicBarrier(THREAD_NUM, new ResultThread());

    private static ConcurrentMap<String, Long> resultMap = new ConcurrentHashMap<>(THREAD_NUM);

    private static class ResultThread implements Runnable {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "ResultThread Run" );
            System.out.println("********************");
            for (ConcurrentMap.Entry result: resultMap.entrySet()){
                System.out.println(result.getKey() + ":" + result.getValue());
            }
            System.out.println("********************");
        }
    }

    private static class BaseThread implements Runnable {

        @Override
        public void run() {
            try {
                resultMap.put(Thread.currentThread().getName(), Thread.currentThread().getId());
                System.out.println("one do something ...");
                barrier.await();
                System.out.println("two do something ...");
                barrier.await();
                System.out.println("BaseThread .. Run end");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < THREAD_NUM; i++) {
            new Thread(new BaseThread()).start();
        }
        SleepTools.ms(10);
        System.out.println("main ... end");
    }
}
