package atomatic;

import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * Created by lqb
 * on 2019/5/22.
 */
public class UseAtomicMarkableReference {
    static AtomicMarkableReference<Integer> reference
            = new AtomicMarkableReference<>(99, false);

    public static void main(String[] args) throws InterruptedException {

        System.out.println(reference.getReference() + " == " + reference.isMarked());

        Thread one = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ": value:" + reference.getReference()
                    + "  stamp:" + reference.isMarked());
            boolean state = reference.compareAndSet(99, 66, false, true);
            System.out.println(state);
        });

        Thread two = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ": value:" + reference.getReference()
                    + "  stamp:" + reference.isMarked());
            boolean state = reference.compareAndSet(99, 88, false, true);
            System.out.println(state);
        });

        one.start();
        one.join();
        two.start();
        two.join();

        System.out.println(reference.getReference() + " == " + reference.isMarked());
    }
}
