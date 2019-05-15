package forkjoin.sort;

import forkjoin.sum._1.MakeArray;


/**
 * Created by lqb
 * on 2019/5/14.
 * 类说明：简单插入排序（升序）
 */
public class InsertionSort {

    public static int[] sort(int[] array) {
        if (array.length == 0) {
            return array;
        }
        /*当前待排序数据，该元素之前的元素均已被排序过*/
        int currentValue;

        for (int i = 0; i < array.length - 1; i++) {
            int preIndex = i;
            currentValue = array[preIndex + 1];

            /*while (preIndex >= 0 && currentValue < array[preIndex]) {
                array[preIndex + 1] = array[preIndex];
                preIndex--;
            }*/
            while (preIndex >= 0 && currentValue > array[preIndex]) {
                array[preIndex + 1] = array[preIndex];
                preIndex--;
            }

            array[preIndex + 1] = currentValue;
        }
        return array;
    }

    public static void main(String[] args) {
        System.out.println("================================");
        /*int [] array = InsertionSort.sort(MakeArray.markArray());
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }*/
        long start = System.currentTimeMillis();
        InsertionSort.sort(MakeArray.markArray());
        long end = System.currentTimeMillis();
        System.out.println("ms:" + (end - start));
    }
}
