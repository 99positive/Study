package my.thread.pool_3;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lqb
 * on 2019/5/13.
 */
public class DBPoolTest {


    static DBPool pool = new DBPool(10);

    static CountDownLatch end;

    public static void main(String[] args) throws InterruptedException {
        final int THREAD_COUNT = 50;
        end = new CountDownLatch(THREAD_COUNT);

        int count = 20;
        AtomicInteger got = new AtomicInteger();
        AtomicInteger notGot = new AtomicInteger();
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(new Work(got, notGot, count)).start();
        }
        end.await();
        System.out.println("总共尝试了: " + (THREAD_COUNT * count));
        System.out.println("拿到连接的次数：  " + got);
        System.out.println("没能连接的次数： " + notGot);
    }

    private static class Work implements Runnable{
        private AtomicInteger got;
        private AtomicInteger notGot;
        private int threadNum;

        public Work(AtomicInteger got, AtomicInteger notGot, int threadNum) {
            this.got = got;
            this.notGot = notGot;
            this.threadNum = threadNum;
        }

        @Override
        public void run() {
            while (threadNum > 0) {
                Connection connection = null;
                try {
                    connection = pool.fetchConnection(1);
                    if (connection != null) {
                        got.incrementAndGet();
                    } else {
                        notGot.incrementAndGet();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    pool.releaseConnection(connection);
                }
                threadNum --;
            }
            end.countDown();
        }
    }
}
