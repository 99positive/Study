package threadwait.semaphore;

import tools.SleepTools;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Created by lqb
 * on 2019/5/17.
 */
public class DBPoolNoUseless {
    //线程池数量
    private static final int INIT_POOL_NUM = 10;
    //可用
    private static Semaphore useful;

    private static LinkedList<Connection> pool = new LinkedList<>();

    static {
        for (int i = 0; i < INIT_POOL_NUM; i++) {
            pool.addLast(SqlConnectImpl.fetchConnection());
        }
    }

    /**
     * 初始化 可用和已用数量
     */
    public DBPoolNoUseless(){
        useful = new Semaphore(INIT_POOL_NUM);
    }

    /**
     * 返回连接给连接池
     * @param connection
     */
    public void returnConnection(Connection connection) {
        if (connection != null) {
            System.out.println("useful等待数据库连接:" + useful.getQueueLength() + " --- useful 可用连接:" + useful.availablePermits());
            synchronized (pool) {
                pool.addLast(connection);
            }
            useful.release();
        }
    }

    /**
     * 获取连接
     * @return
     */
    public Connection takeConnection() {
        Connection connection = null;
        try {
            useful.acquire();
            synchronized (pool) {
                connection = pool.removeFirst();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            return connection;
        }
    }

    private static final DBPoolNoUseless dbPool = new DBPoolNoUseless();

    private static class TempThread extends Thread {

        @Override
        public void run() {
            long start = System.currentTimeMillis();
//            Connection connection = dbPool.takeConnection();
            Random random = new Random();
            System.out.println(Thread.currentThread().getName() + "_RUN_WAIT_[" + (System.currentTimeMillis() - start) + "ms]");
            SleepTools.ms(50 + random.nextInt(100));
            //不接受new Connection()都是正常
//            dbPool.returnConnection(connection);
            //接收new Connection() 异常增加新的【信号量】
            dbPool.returnConnection(SqlConnectImpl.fetchConnection());
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            new TempThread().start();
        }
    }
}
