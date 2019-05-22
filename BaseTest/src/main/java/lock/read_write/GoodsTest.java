package lock.read_write;

import lock.read_write.entity.Good;
import lock.read_write.service.GoodsService;
import lock.read_write.service.impl.GoodsServiceImpl;
import lock.read_write.service.impl.UseRwLock;
import tools.SleepTools;

/**
 * Created by lqb
 * on 2019/5/22.
 */
public class GoodsTest {
    //读写锁的比例
    static final int readWriteRatio = 10;
    //最少线程
    static final int minthreadCount = 3;

    private static class GetThread implements Runnable{

        private GoodsService goodsService;

        public GetThread(GoodsService goodsService) {
            this.goodsService = goodsService;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            for (int i = 0; i < 100; i++) {
                goodsService.getNum();
            }
            System.out.println(Thread.currentThread().getName() + " read 耗时：" + (System.currentTimeMillis() - start));
        }
    }

    private static class SetThread implements Runnable{
        private GoodsService goodsService;

        public SetThread(GoodsService goodsService) {
            this.goodsService = goodsService;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            for (int i = 0; i < 10; i++) {
                SleepTools.ms(50);
                goodsService.setGoodName("test");
            }
            System.out.println(Thread.currentThread().getName() + "write 耗时："+ (System.currentTimeMillis() - start));
        }
    }

    public static void main(String[] args) {
        Good good = new Good("book", 8585, 2020);
        //synchronized
        GoodsService goodsService = new GoodsServiceImpl(good);

        //RwLock
//        GoodsService goodsService = new UseRwLock(good);

        for (int i = 0; i < minthreadCount; i++) {
            Thread setThread = new Thread(new SetThread(goodsService));
            for (int j = 0; j < readWriteRatio; j++) {
                new Thread(new GetThread(goodsService)).start();
            }
            SleepTools.ms(100);
            setThread.start();
        }
    }
}
