package threadwait.semaphore;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * Created by lqb
 * on 2019/5/16.
 */
public class DBPoolSemaphore {
    //线程池数量
    private static final int INIT_POOL_NUM = 10;
    //可用，已用连接
    private static Semaphore useful, useless;

    private static LinkedList<Connection> pool = new LinkedList<>();

    static {
        for (int i = 0; i < INIT_POOL_NUM; i++) {
            pool.addLast(SqlConnectImpl.fetchConnection());
        }
    }

    /**
     * 初始化 可用和已用数量
     */
    public DBPoolSemaphore(){
        useful = new Semaphore(INIT_POOL_NUM);
        useless = new Semaphore(0);
    }

    /**
     * 返回连接给连接池
     * @param connection
     */
    public void returnConnection(Connection connection) {
        try {
            if (connection != null) {
                System.out.println("useful等待数据库连接:" + useful.getQueueLength() + " --- useful 可用连接:" + useful.availablePermits());
                useless.acquire();
                synchronized (pool) {
                    pool.addLast(connection);
                }
                useful.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
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
            useless.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            return connection;
        }
    }
}
