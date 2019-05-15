package forkjoin.sum;

import tools.SleepTools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Created by lqb
 * on 2019/5/13.
 */
public class SumArray {

    private static class SumTask extends RecursiveTask<Integer> {

        /*阈值*/
        private final static int THRESHOLD = MakeArray.ARRAY_LENGTH / 10;
        private int[] src;
        private int fromIndex;
        private int toIndex;

        public SumTask(int[] src, int fromIndex, int toIndex) {
            this.src = src;
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
        }

        @Override
        protected Integer compute() {
            if (toIndex - fromIndex < THRESHOLD) {
                int count = 0;
                for (int i = fromIndex; i <= toIndex; i++) {
                    SleepTools.ms(1);
                    count = count + src[i];
                }
                return count;
            } else {
                int mid = (fromIndex + toIndex) / 2;
                SumTask left = new SumTask(src, fromIndex, mid);
                SumTask right = new SumTask(src, mid + 1, toIndex);
                invokeAll(left, right);
                return left.join() + right.join();
            }
        }

        public static void main(String[] args) {
            int[] src = MakeArray.markArray();

            /* new 出池的实例 */
            ForkJoinPool pool = new ForkJoinPool();

            /* new Task的实例 */
            SumTask innerFind = new SumTask(src, 0, src.length - 1);

            long start = System.currentTimeMillis();
            pool.invoke(innerFind);
            System.out.println("The count is " + innerFind.join() + " speed time:" + (System.currentTimeMillis() - start) + "ms");
        }
    }

}
