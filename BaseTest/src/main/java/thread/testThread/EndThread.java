package thread.testThread;

import java.lang.management.ManagementFactory;

/**
 * Created by lqb
 * on 2019/4/27.
 */
public class EndThread {

    public static class MyThread extends Thread {

        public MyThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " interrupt flag" + isInterrupted());
//            while (!isInterrupted()) {
//            while (!Thread.interrupted()) {
            while (true) {
                System.out.println(threadName + " is running");
                System.out.println(threadName + " inner interrupt flag = " + isInterrupted());
            }
//            System.out.println(threadName + " interrupt flag = " + isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new MyThread("endThread");
        thread.start();
        Thread.sleep(20);
        thread.interrupt();
    }
}
