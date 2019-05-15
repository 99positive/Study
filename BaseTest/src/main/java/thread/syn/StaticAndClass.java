package thread.syn;

import tools.SleepTools;

/**
 * Created by lqb
 * on 2019/4/27.
 */
public class StaticAndClass {

    private static class SynClass extends Thread{

        @Override
        public void run() {
            System.out.println("SynClass is running...");
            synClass();
        }
    }

    private static class SynStatic extends Thread {
        @Override
        public void run() {
            System.out.println(currentThread().getName() + "Synstatic is running...");
            synStatic();
        }
    }

    private static synchronized void synClass() {
        System.out.println(Thread.currentThread().getName() + "synClass going..");
        SleepTools.second(2);
        System.out.println(Thread.currentThread().getName() + "synClass end");
    }

    private static Object object = new Object();

    private static void synStatic() {
        synchronized (object) {
            System.out.println(Thread.currentThread().getName() + "synStatic going...");
            SleepTools.second(1);
            System.out.println(Thread.currentThread().getName() + "synStatic end");
        }
    }

    public static void main(String[] args) {
        Thread t1 = new SynClass();
//        Thread t2 = new SynClass();
        Thread t2 = new SynStatic();
        t2.start();
        t1.start();
        SleepTools.second(2);
    }
}
