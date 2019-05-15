package my.thread.pc_test;

import tools.SleepTools;

import java.util.Random;

/**
 * Created by lqb
 * on 2019/5/4.
 */
public class PC_0504 {

    private static Execute execute = new Execute();

    private static class P extends Thread{
        @Override
        public void run() {
            while (true) {
                execute.increase();
                Random random = new Random();
                SleepTools.ms(random.nextInt(1000) * execute.getP_max());
            }
        }
    }

    private static class C extends Thread{
        @Override
        public void run() {
            while (true) {
                execute.reduce();
                Random random = new Random();
                SleepTools.ms(random.nextInt(1000) * execute.getC_max());
            }
        }
    }

    public static void main(String[] args) {
        int num = 5;

        for (int i = 0; i < num; i++) {
            new C().start();
        }

        for (int i = 0; i < num; i++) {
            new P().start();
        }

        SleepTools.second(3);
    }

}
