package my.thread.pool_1;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * Created by lqb
 * on 2019/5/5.
 */
public class DBPool {

    private static LinkedList<Connection> pool = new LinkedList<>();

    /**
     * 限制连接池大小，并初始化
     * @param initialState
     */
    public DBPool(int initialState) {
        if (initialState > 0) {
            for (int i=0; i < initialState; i++) {
                pool.addLast(SqlConnectImpl.fetchConnection());
            }
        }
    }

    /**
     * 释放连接池，将连接归还到连接池
     * @param connection
     */
    public void releaseConnection(Connection connection) {
        if (connection != null) {
            synchronized (pool) {
                //todo 去释放连接池
                //归还连接给连接池
                pool.addLast(connection);
                pool.notifyAll();
            }
        }
    }

    public Connection fetchConnection(long mills) throws InterruptedException {
        synchronized (pool) {
            if (mills <= 0) {
                while (pool.isEmpty()) {
                    pool.wait();
                }
                return pool.removeFirst();
            } else {
                long future = System.currentTimeMillis() + mills;
                long remaining = mills;
                while (pool.isEmpty() && remaining > 0) {
                    pool.wait(remaining);
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
