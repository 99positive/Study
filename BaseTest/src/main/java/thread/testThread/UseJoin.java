package thread.testThread;

/**
 * Created by lqb
 * on 2019/4/27.
 */
public class UseJoin {

    static class Ause implements Runnable {

        private Thread thread;

        public Ause(Thread thread) {
            this.thread = thread;
        }

        public Ause() {

        }

        @Override
        public void run() {
            System.out.println("Ause 开始");
            try {
                if (thread != null) thread.join();
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + " Ause 结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    static class AuseFriend implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(2000);
                System.out.println("AuseFriend 开始");
                System.out.println(Thread.currentThread().getName() + " AuseFriend 结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = Thread.currentThread();

        AuseFriend auseFriend = new AuseFriend();
        Thread friend = new Thread(auseFriend);

        Ause ause = new Ause(friend);
        Thread grid = new Thread(ause);
        friend.start();
        Thread.sleep(2000);
        grid.start();
        System.out.println("main start");
        Thread.sleep(2000);
        grid.join();
        System.out.println("main end");

    }

}
