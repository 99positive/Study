package atomatic;

import tools.SleepTools;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lqb
 * on 2019/5/22.
 */
public class RunAtomicInt {
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public void increase() {
        for (;;){
            int i = this.getCount();
            boolean state = this.compareAndSet(i, i + 1);
            if (state) {
                break;
            }
        }
    }

    public int getCount(){
        return atomicInteger.get();
    }

    public boolean compareAndSet(int expect, int result) {
        return atomicInteger.compareAndSet(expect, result);
    }

    public static void main(String[] args) {
        RunAtomicInt atomicInt = new RunAtomicInt();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    atomicInt.increase();
                }
            }).start();
        }

        SleepTools.ms(3000);
        System.out.println(atomicInt.getCount());
    }
}
