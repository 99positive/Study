package lock.read_write.service.impl;

import lock.read_write.entity.Good;
import lock.read_write.service.GoodsService;
import tools.SleepTools;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by lqb
 * on 2019/5/22.
 */
public class UseRwLock implements GoodsService {

    private Good good;

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    public UseRwLock(Good good) {
        this.good = good;
    }

    @Override
    public int getNum() {
        readLock.lock();
        try{
            SleepTools.ms(5);
            return good.getStoreNumber();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean setGoodName(String name) {
        writeLock.lock();
        try {
            SleepTools.ms(5);
            good.setName(name);
            return true;
        } finally {
            writeLock.unlock();
        }
    }
}
