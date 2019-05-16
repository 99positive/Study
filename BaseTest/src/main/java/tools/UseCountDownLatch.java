package tools;

import java.util.concurrent.CountDownLatch;

/**
 * Created by lqb
 * on 2019/5/16.
 */
public class UseCountDownLatch {

    static CountDownLatch countDownLatch = new CountDownLatch(7);

    private static class InitThread implements Runnable {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "__RUN__" + Thread.currentThread().getId());
            countDownLatch.countDown();
            System.out.println(countDownLatch.getCount());
        }
    }

    private static class RunThread implements Runnable {

        @Override
        public void run() {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Run RunThread");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            System.out.println("run 1");
            countDownLatch.countDown();
            SleepTools.ms(10);
            System.out.println("Run_2");
            countDownLatch.countDown();
//            SleepTools.ms(10);
            System.out.println("wait!!");
        }).start();
        RunThread runThread = new RunThread();
        InitThread initThread = new InitThread();
        new Thread(runThread).start();
        for (int i = 0; i < 5; i++) {
            new Thread(initThread).start();
        }
        countDownLatch.await();
        System.out.println("end...");
    }
}
