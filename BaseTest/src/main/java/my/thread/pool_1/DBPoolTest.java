package my.thread.pool_1;

import javafx.concurrent.Worker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lqb
 * on 2019/5/5.
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
            Thread thread = new Thread(new Worker(count, got, notGot), "WORK_" + i);
            thread.start();
        }

        end.await();
        System.out.println("总共尝试了: " + (THREAD_COUNT * count));
        System.out.println("拿到连接的次数：  " + got);
        System.out.println("没能连接的次数： " + notGot);
    }

    static class Worker implements Runnable{

        int count;
        AtomicInteger got;
        AtomicInteger notGot;

        public Worker(int count, AtomicInteger got, AtomicInteger notGot) {
            this.count = count;
            this.got = got;
            this.notGot = notGot;
        }

        @Override
        public void run() {
            while (count > 0) {
                try {
                    Connection connection = pool.fetchConnection(1);
                    if (connection != null) {
                        try {
                            connection.createStatement();
//                            PreparedStatement preparedStatement
//                                    = connection.prepareStatement("");
//                            preparedStatement.execute();
                            connection.commit();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } finally {
                            pool.releaseConnection(connection);
                            got.incrementAndGet();
                        }
                    } else {
                        notGot.incrementAndGet();
                        System.out.println(Thread.currentThread().getName() + "等待超时！");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    count --;
                }
            }
            end.countDown();
        }
    }
}
