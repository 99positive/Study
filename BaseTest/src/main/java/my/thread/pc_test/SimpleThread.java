package my.thread.pc_test;

import tools.SleepTools;

import java.util.LinkedList;

/**
 * Created by lqb
 * on 2019/5/5.
 */
public class SimpleThread {

    private static LinkedList<Integer> linkedList = new LinkedList<>();

    private static int zd = 8;

    private static class Producer extends Thread {

        @Override
        public void run() {
            synchronized (linkedList) {
                try {
                    while (true){
                        while (linkedList.size() >= 20) {
                            linkedList.wait();
                        }
                        linkedList.addLast(zd);
                        System.out.println("添加子弹！剩余子弹数：" + linkedList.size());
                        SleepTools.ms(30);
                        linkedList.notifyAll();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class Consumer extends Thread {
        @Override
        public void run() {
            synchronized (linkedList) {
                try {
                    while (true) {
                        while (linkedList.isEmpty()){
                            linkedList.wait();
                        }
                        linkedList.removeFirst();
                        System.out.println("消费子弹！剩余子弹数：" + linkedList.size());
                        SleepTools.ms(30);
                        linkedList.notifyAll();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        int max = 5;
        for (int i = 0; i < max; i++){
            new Producer().start();
        }
        for (int i = 0; i < max; i++){
            new Consumer().start();
        }
        /*Producer[] producerArr = new Producer[max];
        Consumer[] consumerArr = new Consumer[max];
        for (Producer thread : producerArr){
            thread.start();
        }
        for (Consumer thread : consumerArr){
            thread.start();
        }*/
        SleepTools.second(2);
    }
}
