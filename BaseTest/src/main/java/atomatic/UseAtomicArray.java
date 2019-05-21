package atomatic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Created by lqb
 * on 2019/5/22.
 */
public class UseAtomicArray {
    static int[] array = new int[]{1,3,5};
    static AtomicIntegerArray integerArray = new AtomicIntegerArray(array);

    public static void main(String[] args) {
        System.out.println(integerArray.compareAndSet(0, 1, 10));
        System.out.println(array[0]);
        System.out.println(integerArray.get(0));
    }
}
