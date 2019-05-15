package my.thread.pool_3;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * Created by lqb
 * on 2019/5/12.
 */
public class DBPool {

    private static LinkedList<Connection> pool = new LinkedList<>();

    public DBPool(int initNum) {
        for (int i = 0; i < initNum; i++) {
            pool.addLast(SqlConnectImpl.fetchConnection());
        }
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            synchronized (pool) {
                pool.addLast(connection);
                pool.notifyAll();
            }
        }
    }

    public Connection fetchConnection(long mills) throws Exception{
        synchronized (pool){
            if (mills < 0) {
                while (pool.isEmpty()) {
                    pool.wait();
                }
                return pool.removeFirst();
            } else {
                long currentNum = System.currentTimeMillis() + mills;
                long num = mills;
                while (pool.isEmpty() && num > 0) {
                    pool.wait();
                    num = currentNum - System.currentTimeMillis();
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
