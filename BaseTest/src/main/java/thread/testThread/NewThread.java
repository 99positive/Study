package thread.testThread;

import java.lang.management.ThreadInfo;

/**
 * Created by lqb
 * on 2019/4/27.
 */
public class NewThread {

    private static class UseThread extends Thread{
        @Override
        public void run() {
            super.run();
            System.out.println("thread");
        }
    }

    private static class UseRunnable implements Runnable{

        @Override
        public void run() {
            System.out.println("runnable");
        }
    }

    public static void main(String[] args) {

        UseThread thread = new UseThread();
        thread.start();

        UseRunnable runnable = new UseRunnable();
        new Thread(runnable).start();
    }
}
