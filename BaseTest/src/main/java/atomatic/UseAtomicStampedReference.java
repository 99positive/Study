package atomatic;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * Created by lqb
 * on 2019/5/22.
 */
public class UseAtomicStampedReference {
    static AtomicStampedReference<String> atomicStampedReference
            = new AtomicStampedReference<>("test", 0);

    public static void main(String[] args) throws InterruptedException {
        final int oldStamp = atomicStampedReference.getStamp();
        final String oldValue = atomicStampedReference.getReference();
        System.out.println(oldStamp + " " + oldValue);

        Thread one = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ": value:" + atomicStampedReference.getReference()
                + "  stamp:" + atomicStampedReference.getStamp());
            boolean state = atomicStampedReference.compareAndSet("test", "back", 0, 1);
            System.out.println(state);
        });

        Thread two = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ": value:" + atomicStampedReference.getReference()
                    + "  stamp:" + atomicStampedReference.getStamp());
            boolean state = atomicStampedReference.compareAndSet("test", "tttt", 0, 1);
            System.out.println(state);
        });

        one.start();
        one.join();

        two.start();
        two.join();

        System.out.println(atomicStampedReference.getStamp() + " -- " + atomicStampedReference.getReference());
    }
}
