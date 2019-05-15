package thread.testThread;

/**
 * Created by lqb
 * on 2019/4/27.
 */
public class DaemonThread {

    static class DaemonTest extends Thread{

        @Override
        public void run() {
            try {
                while (!isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + " extends Thread");
                }
                System.out.println(Thread.currentThread().getName() + " interrupt flag is " + isInterrupted());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //守护线程中finally不一定起作用
                System.out.println(" .. finally ..");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DaemonTest daemonTest = new DaemonTest();
        daemonTest.setPriority(10);
        daemonTest.setDaemon(true);
        daemonTest.start();
        Thread.sleep(5);
//        daemonTest.interrupt();
    }
}
