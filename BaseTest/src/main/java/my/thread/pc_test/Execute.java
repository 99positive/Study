package my.thread.pc_test;

import tools.SleepTools;

/**
 * Created by lqb
 * on 2019/5/4.
 */
public class Execute {
    private int count = 10;

    private int p_max = 1;

    private int c_max = 1;

    public int getP_max() {
        return p_max;
    }

    public void setP_max(int p_max) {
        this.p_max = p_max;
    }

    public int getC_max() {
        return c_max;
    }

    public void setC_max(int c_max) {
        this.c_max = c_max;
    }

    public Execute(){

    }

    public Execute(int count){
        this.count = count;
    }

    public synchronized void increase(){
        if (this.count < 20) {
            this.count++;
            System.out.println("P 子弹数：" + count);
            notifyAll();
        } else {
            try {
                wait();
                System.out.println("不能生产子弹，已达最大量！");
                this.p_max = 2;
                this.c_max = 1;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void reduce() {
        if (this.count > 0) {
            this.count--;
            notifyAll();
            System.out.println("C 子弹数：" + count);
        } else {
            try {
                wait();
                System.out.println("不能消费子弹，已到空！");
                this.p_max = 1;
                this.c_max = 2;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
