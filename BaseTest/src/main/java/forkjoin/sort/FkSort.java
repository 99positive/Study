package forkjoin.sort;

import forkjoin.sum._1.MakeArray;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Created by lqb
 * on 2019/5/15.
 */
public class FkSort {

    private static class SumTask extends RecursiveTask<int[]> {

        private final static int THRESHOLD = 10000;
        private int[] src;

        public SumTask(int[] src) {
            this.src = src;
        }

        @Override
        protected int[] compute() {
            if (src.length <= THRESHOLD) {
                return InsertionSort.sort(src);
            } else {
                int mid = src.length / 2;
                SumTask left = new SumTask(Arrays.copyOfRange(src, 0, mid));
                SumTask right = new SumTask(Arrays.copyOfRange(src, mid, src.length - 1));
                invokeAll(left, right);
                int [] leftResult = left.join();
                int [] rightResult = right.join();
                return MergeSort.merge(leftResult, rightResult);
            }
        }
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        int [] array = MakeArray.markArray();
        SumTask sumTask = new SumTask(array);
        long start = System.currentTimeMillis();
        pool.submit(sumTask);
        System.out.println("哈哈");
        sumTask.join();
        System.out.println("ms:" + (System.currentTimeMillis() - start));
    }
}
