package forkjoin;

import tools.SleepTools;

import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by lqb
 * on 2019/5/16.
 */
public class SecretWorkFetch {
    private static class Work implements Runnable {
        private static Object object = new Object();
        private static int count = 0;
        public int id = 0;
        private long putThread;

        public Work() {
            synchronized (object) {
                id = count ++;
            }
        }

        @Override
        public void run() {
            if (Thread.currentThread().getId() != putThread) {
                System.out.println("-------------");
            }
            System.out.println(Thread.currentThread().getId() + ":" + putThread + "// finish Job" + id);
        }

        public long getPutThread() {
            return putThread;
        }

        public void setPutThread(long putThread) {
            this.putThread = putThread;
        }
    }

    public static Work generateWork() {
        return new Work();
    }

    private static class ConsumerAndProducer implements Runnable {

        private Random random = new Random();
        private final LinkedBlockingDeque<Work> deque;
        private final LinkedBlockingDeque<Work> otherWork;
        public ConsumerAndProducer(LinkedBlockingDeque<Work> deque, LinkedBlockingDeque<Work> otherWork) {
            this.deque = deque;
            this.otherWork = otherWork;
        }
        @Override
        public void run() {
            while (!Thread.interrupted()) {
                try {
                    SleepTools.ms(1000);
                    if (random.nextBoolean()) {
                        int count = random.nextInt(5);
//                        System.out.println("*****" + count + "*****");
                        for (int i = 0; i < count; i++) {
                            Work work = generateWork();
                            work.setPutThread(Thread.currentThread().getId());
                            deque.putLast(work);
                        }
                    }

                    if (deque.isEmpty()) {
                        if (!otherWork.isEmpty()) {
                            System.out.println("otherWork is run:");
                            otherWork.takeLast().run();
                        }
                    } else {
                        deque.takeFirst().run();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public static void main(String[] args) {
            LinkedBlockingDeque<Work> deque = new LinkedBlockingDeque<>();
            LinkedBlockingDeque<Work> other = new LinkedBlockingDeque<>();

            new Thread(new ConsumerAndProducer(deque, other)).start();
            new Thread(new ConsumerAndProducer(deque, other)).start();

            new Thread(new ConsumerAndProducer(other, deque)).start();
            new Thread(new ConsumerAndProducer(other, deque)).start();

        }
    }
}
