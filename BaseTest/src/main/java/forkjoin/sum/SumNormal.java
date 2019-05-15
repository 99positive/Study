package forkjoin.sum;

import tools.SleepTools;

/**
 * Created by lqb
 * on 2019/5/13.
 */
public class SumNormal {
    public static void main(String[] args) {
        int count = 0;
        int [] src = MakeArray.markArray();

        long start = System.currentTimeMillis();
        for (int i = 0; i < src.length; i++) {
            SleepTools.ms(1);
            count = count + src[i];
        }
        System.out.println("The count is " + count + " speed time: " + (System.currentTimeMillis() - start) + "ms");
    }
}
