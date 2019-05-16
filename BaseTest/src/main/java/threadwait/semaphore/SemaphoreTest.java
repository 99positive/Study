package threadwait.semaphore;

import tools.SleepTools;

import java.sql.Connection;
import java.util.Random;

/**
 * Created by lqb
 * on 2019/5/16.
 */
public class SemaphoreTest {
    private static final DBPoolSemaphore pool = new DBPoolSemaphore();

    private static class RunThread implements Runnable {

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            Random random = new Random();
            Connection connection = pool.takeConnection();
            System.out.println(Thread.currentThread().getName() + "_RUN_WAIT_[" + (System.currentTimeMillis() - start) + "ms]");
            SleepTools.ms(50 + random.nextInt(100));
            pool.returnConnection(connection);
//            pool.returnConnection(new SqlConnectImpl());

        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            new Thread(new RunThread()).start();
        }
    }
}
