package forkjoin.sort;

import forkjoin.sum._1.MakeArray;

import java.util.Arrays;

/**
 * Created by lqb
 * on 2019/5/13.
 */
public class MergeSort {
    public static int[] sort(int [] array) {
        if (array.length <= MakeArray.THRESHOLD){
            return InsertionSort.sort(array);
        } else {
            int mid = array.length / 2;
            int [] left = Arrays.copyOfRange(array, 0, mid);
            int [] right = Arrays.copyOfRange(array, mid, array.length);
            return merge(sort(left), sort(right));
        }
    }

    public static int[] merge(int [] left, int[] right) {
        int [] result = new int[left.length + right.length];
        for (int index = 0, i = 0, j = 0; index < result.length; index++) {
            if (i >= left.length) {
                result[index] = right[j++];
            } else if (j >= right.length) {
                result[index] = left[i++];
            } else if (left[i] < right[j]) {
                result[index] = right[j++];
            } else {
                result[index] = left[i++];
            }
        }
        /*for (int i = 0; i < result.length; i++) {
            System.out.print(result[i] + " ");
        }
        System.out.println();*/
        return result;
    }

    public static void main(String[] args) {
        System.out.println("====================================");
        long start = System.currentTimeMillis();
        int []array = MergeSort.sort(MakeArray.markArray());
        /*for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }*/
        System.out.println("ms:" + (System.currentTimeMillis() - start));
    }
}
