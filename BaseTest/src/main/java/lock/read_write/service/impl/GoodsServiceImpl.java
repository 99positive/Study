package lock.read_write.service.impl;

import lock.read_write.entity.Good;
import lock.read_write.service.GoodsService;
import tools.SleepTools;


/**
 * Created by lqb
 * on 2019/5/22.
 */
public class GoodsServiceImpl implements GoodsService {
    private Good good;

    public GoodsServiceImpl(Good good) {
        SleepTools.ms(3);
        this.good = good;
    }

    @Override
    public synchronized int getNum() {
        return good.getStoreNumber();
    }

    @Override
    public synchronized boolean setGoodName(String name) {
        SleepTools.ms(3);
        good.setName(name);
        return true;
    }
}
