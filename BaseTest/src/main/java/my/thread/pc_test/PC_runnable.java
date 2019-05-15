package my.thread.pc_test;

/**
 * Created by lqb
 * on 2019/4/28.
 */
public class PC_runnable {

    private static class Result {
        private int count;

        public void increase() {
            System.out.println(Thread.currentThread().getName() + " ..生产数量：" + count++ + "..." + count);
        }
        public void reduce() {
            System.out.println(Thread.currentThread().getName() + " ..消费数量：" + count-- + "..." + count);
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    private static class Producer implements Runnable{

        private Result result;

        public Producer(Result result) {
            this.result = result;
        }

        @Override
        public void run() {
            synchronized (result) {
                try {
                    while (true) {
                        if (result.getCount() >= 20) {
                            result.wait();
                        }
                        result.increase();
                        result.notify();
                        Thread.sleep(100);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class Consumer implements Runnable{
        private Result result;

        public Consumer(Result result){
            this.result = result;
        }

        @Override
        public void run() {
            synchronized (result) {
                try {
                    while (true) {
                        if (result.getCount() <= 0) {
                            result.wait();
                        }
                        result.reduce();
                        result.notify();
                        Thread.sleep(100);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        final int MAX_THREAD = 10;
        Result result = new Result();
        Consumer consumer = new Consumer(result);
        Producer producer = new Producer(result);

        for (int i = 0; i < MAX_THREAD; i++) {
            new Thread(consumer).start();
        }
        for (int i = 0; i < MAX_THREAD; i++) {
            new Thread(producer).start();
        }

    }
}
