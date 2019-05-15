package my.thread.pc_test;

import tools.SleepTools;

import java.util.LinkedList;

/**
 * Created by lqb
 * on 2019/5/6.
 */
public class PC_01 {
    private static LinkedList<String> linkedList = new LinkedList<>();

    private static class Producer implements Runnable{

        @Override
        public void run() {
            while (true) {
                synchronized (linkedList) {
                    try {
                        while (linkedList.size() >= 20) {
                            linkedList.wait();
                        }
                        linkedList.addLast("A");
                        System.out.println(Thread.currentThread().getName() + " add :" + linkedList.size());
                        SleepTools.ms(100);
                        linkedList.notifyAll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
//                SleepTools.ms(10);
            }

        }
    }

    private static class Consumer implements Runnable {

        @Override
        public void run() {
            while (true) {
                synchronized (linkedList) {
                    try {
                        while (linkedList.isEmpty()) {
                            linkedList.wait();
                        }
                        linkedList.removeFirst();
                        System.out.println(Thread.currentThread().getName() + " sub :" + linkedList.size());
                        SleepTools.ms(100);
                        linkedList.notifyAll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
//                SleepTools.ms(10);
            }
        }
    }

    public static void main(String[] args) {
        Producer producer = new Producer();
        Consumer consumer = new Consumer();

        for (int i=0; i < 5; i++){
            new Thread(producer).start();
            new Thread(consumer).start();
        }

        SleepTools.second(1);
    }
}
