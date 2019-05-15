package thread.syn;

/**
 * Created by lqb
 * on 2019/4/27.
 * 演示实例锁和类锁匙不同的，两者可以并行
 */
public class InstanceAndClass {

    private static class SynClass extends Thread{

        @Override
        public void run() {
            System.out.println("TestClass is running...");
            synClass();
        }
    }

    private static class InstanceSyn implements Runnable {

        private InstanceAndClass SynClassAndInstance;

        private InstanceSyn(InstanceAndClass SynClassAndInstance) {
            this.SynClassAndInstance = SynClassAndInstance;
        }

        @Override
        public void run() {
            try {
                System.out.println("TestInstance is running..." + SynClassAndInstance);
                SynClassAndInstance.instance();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void instance() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("synInstance is going..." + this.toString());
        Thread.sleep(1000);
        System.out.println("synInstance ended " + this.toString());
    }

    private static synchronized void synClass() {
        try {
            Thread.sleep(1000);
            System.out.println("synClass going...");
            Thread.sleep(1000);
            System.out.println("synClass end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        InstanceAndClass instanceAndClass = new InstanceAndClass();
        Thread t1 = new SynClass();
        Thread t2 = new Thread(new InstanceSyn(instanceAndClass));
        t2.start();
        Thread.sleep(1111);
        t1.start();
    }
}
