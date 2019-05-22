package lock.read_write.entity;

import tools.SleepTools;

/**
 * Created by lqb
 * on 2019/5/22.
 */
public class Good {
    private String name;

    private double totalMoney;

    private int storeNumber;

    public Good(String name, double totalMoney, int storeNumber) {
        this.name = name;
        this.totalMoney = totalMoney;
        this.storeNumber = storeNumber;
    }

    public String getName() {
        SleepTools.ms(5);
        return name;
    }

    public void setName(String name) {
        SleepTools.ms(5);
        this.name = name;
    }

    public double getTotalMoney() {
        SleepTools.ms(5);
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        SleepTools.ms(5);
        this.totalMoney = totalMoney;
    }

    public int getStoreNumber() {
        SleepTools.ms(5);
        return storeNumber;
    }

    public void setStoreNumber(int storeNumber) {
        SleepTools.ms(5);
        this.storeNumber = storeNumber;
    }
}
