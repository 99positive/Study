package threadwait.future;

import tools.SleepTools;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by lqb
 * on 2019/5/21.
 */
public class UseFuture {

    /**
     * 实现Callable
     */
    private static class UseCallable implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            int sum = 0;
            System.out.println("run");
//            SleepTools.ms(200);
            for (int i = 0; i < 1000; i++) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Callable...计算任务中断");
                    return null;
                }
                sum += i;
                System.out.println("sum:" + sum);
            }
            return sum;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        UseCallable callable = new UseCallable();

        FutureTask<Integer> futureTask = new FutureTask<>(callable);

        new Thread(futureTask).start();

        Random random = new Random();
        int result = random.nextInt(100);
        System.out.println("random result: " + result);
        SleepTools.ms(1);
        if (result > 70) {
            System.out.println("get result:" + futureTask.get());
        } else {
            System.out.println("cancel....");
            futureTask.cancel(true);
        }

    }
}
