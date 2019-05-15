package thread.pool;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * Created by lqb
 * on 2019/5/4.
 */
public class DBPool {

    private static LinkedList<Connection> pool = new LinkedList<Connection>();

    /**
     * 限制池的大小
     * @param initialState 20
     */
    public DBPool(int initialState) {
        if (initialState > 0) {
            for (int i = 0; i < initialState; i++) {
                pool.addLast(SqlConnectImpl.fetchConnection());
            }
        }
    }

    /**
     * 释放连接池
     * @param connection
     */
    public void releaseConnection(Connection connection) {
        if (connection != null) {
            synchronized (pool) {
                pool.addLast(connection);
                pool.notifyAll();
            }
        }
    }

    /**
     * 拿连接
     * 在mills内无法获取到连接，返回null
     * @param mills
     * @return
     * @throws InterruptedException
     */
    public Connection fetchConnection(long mills) throws InterruptedException {
        synchronized (pool) {
            //永不超时
            if (mills < 0) {
                while (pool.isEmpty()) {
                    pool.wait();
                }
                return pool.removeFirst();
            } else {
                /* 超时时刻 */
                long future = System.currentTimeMillis() + mills;
                /* 等待时长 */
                long remaining = mills;
                while (pool.isEmpty() && remaining > 0) {
                    pool.wait(remaining);
                    /* 唤醒一次 重新计算等待时间 */
                    remaining = future - System.currentTimeMillis();
                }
                Connection connection = null;
                if (!pool.isEmpty()) {
                    connection = pool.removeFirst();
                }
                return connection;
            }
        }
    }
}
