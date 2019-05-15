package my.thread.pool_2;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * Created by lqb
 * on 2019/5/6.
 */
public class DBPool {
    private LinkedList<Connection> pool = new LinkedList<>();

    public DBPool(int initSize) {
        while (pool.isEmpty() || pool.size() < initSize){
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

    public Connection fetchConnect(long mills) throws InterruptedException {
        synchronized (pool){
            if (mills <= 0) {
                while (pool.isEmpty()) {
                    pool.wait();
                }
                return pool.removeFirst();
            } else {
                long fetchTime = System.currentTimeMillis() + mills;
                long time = mills;
                while (pool.isEmpty() && time > 0) {
                    pool.wait(time);
                    time = fetchTime - System.currentTimeMillis();
                }
                Connection connection = null;
                if (!pool.isEmpty()){
                    connection = pool.removeFirst();
                }
                return connection;
            }
        }
    }
}
