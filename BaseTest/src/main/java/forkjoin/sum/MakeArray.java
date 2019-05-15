package forkjoin.sum;

import java.util.Random;

/**
 * Created by lqb
 * on 2019/5/13.
 */
public class MakeArray {
    public static final int ARRAY_LENGTH = 88888888;
    public static final int THRESHOLD = 50;

    public static int[] markArray() {
        Random random = new Random();
        int [] result = new int[ARRAY_LENGTH];
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            result[i] = random.nextInt(100);
        }
        return result;
    }
}
