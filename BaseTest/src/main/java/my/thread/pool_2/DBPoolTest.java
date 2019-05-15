package my.thread.pool_2;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lqb
 * on 2019/5/6.
 */
public class DBPoolTest {
    static DBPool pool = new DBPool(5);

    static CountDownLatch end;

    public static void main(String[] args) throws InterruptedException {
        final int THREAD_COUNT = 50;
        end = new CountDownLatch(THREAD_COUNT);
        int count = 20;
        AtomicInteger got = new AtomicInteger();
        AtomicInteger notGot = new AtomicInteger();
        for (int j = 0; j < THREAD_COUNT; j++) {
            new Thread(new Worker(count, got, notGot), "work_" + j).start();
        }
        end.await();
        System.out.println("总共尝试了: " + (THREAD_COUNT * count));
        System.out.println("拿到连接的次数：  " + got);
        System.out.println("没能连接的次数： " + notGot);
    }

    private static class Worker implements Runnable {

        private int count;

        private AtomicInteger got;

        private AtomicInteger notGot;

        public Worker(int count, AtomicInteger got, AtomicInteger notGot) {
            this.count = count;
            this.got = got;
            this.notGot = notGot;
        }

        @Override
        public void run() {
            while (count > 0) {
                try {
                    Connection connection = pool.fetchConnect(1);

                    if (connection != null) {
                        try {
//                            System.out.println("not null!");
                            connection.createStatement();
                            connection.commit();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }finally {
                            pool.releaseConnection(connection);
                            got.incrementAndGet();
                        }
                    } else {
                        notGot.incrementAndGet();
                        System.out.println("等待超时！");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count--;
            }
            end.countDown();
        }
    }
}
