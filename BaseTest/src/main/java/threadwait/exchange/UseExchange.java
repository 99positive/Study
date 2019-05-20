package threadwait.exchange;

import tools.SleepTools;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Exchanger;

/**
 * Created by lqb
 * on 2019/5/21.
 * 交换两个线程的数据
 */
public class UseExchange {

    private static final Exchanger<Set<String>> exchange = new Exchanger<>();

    public static void main(String[] args) {

        new Thread(() -> {
            //存放线程A的数据
            Set<String> setA = new HashSet<>();
            try {
                setA.add("A THREAD START");
                setA.add("sed");
                setA = exchange.exchange(setA);
                System.out.println("Run A");
                for (String data: setA){
                    System.out.println("A:" + data);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            Set<String> setB = new HashSet<>();
            setB.add("B THREAD START");
            try {
                setB.add("happy");
                setB = exchange.exchange(setB);
                System.out.println("Run B");
                for (String data : setB) {
                    System.out.println("B:" + data);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        SleepTools.ms(100);
    }
}
