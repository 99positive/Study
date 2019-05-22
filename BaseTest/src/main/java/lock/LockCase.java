package lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by lqb
 * on 2019/5/22.
 */
public class LockCase {
    private int age = 100000;

    private Lock lock = new ReentrantLock();

    private static class TestThread extends Thread {
        private LockCase lockCase;

        public TestThread(LockCase lockCase, String name){
            super(name);
            this.lockCase = lockCase;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100000; i++) {
                lockCase.test();
            }
            System.out.println(Thread.currentThread().getName() + " -- age:" + lockCase.getAge());
        }
    }

    private void test() {
        lock.lock();
        try {
            age--;
        } finally {
            lock.unlock();
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static void main(String[] args) {
        LockCase lockCase = new LockCase();
        new TestThread(lockCase, "Test").start();
    }
}
