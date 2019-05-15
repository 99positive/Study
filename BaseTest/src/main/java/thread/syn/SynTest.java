package thread.syn;

/**
 * Created by lqb
 * on 2019/4/27.
 */
public class SynTest {
    private static class Count extends Thread{
        private SynTest synTest;

        public Count(SynTest synTest) {
            this.synTest = synTest;
        }

        @Override
        public void run() {
//            synchronized (this) {
                for (int i = 0; i < 10000; i++) {
                    synTest.incCount();
                }
//            }
        }
    }

    private int count;

    private Object object = new Object();

    private void incCount() {
        synchronized (object){
            count++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SynTest synTest = new SynTest();
        Count thread1 = new Count(synTest);
        Count thread2 = new Count(synTest);

        thread1.start();
        thread2.start();

        Thread.sleep(2000);
        System.out.println(synTest.count);
    }
}
