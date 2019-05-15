package my.thread.pc_test;

import tools.SleepTools;

/**
 * Created by lqb
 * on 2019/4/28.
 */
public class ProducerConsumer {

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

    private static class Producer extends Thread{
        private Result result;

        public Producer(Result result){
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

    private static class Consumer extends Thread{
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
        Result result = new Result();
        for(int i = 0; i < 3; i++) {
            new Producer(result).start();
        }
        for(int i = 0; i < 3; i++) {
            new Consumer(result).start();
        }
        /*Producer producer = new Producer(result);
        Consumer consumer = new Consumer(result);

        producer.start();
        consumer.start();*/
        SleepTools.second(2);
    }
}
