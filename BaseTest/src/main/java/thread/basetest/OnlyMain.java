package thread.basetest;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * Created by lqb
 * on 2019/4/26.
 */
public class OnlyMain {

    public static void main(String[] args) {
        //Java虚拟机线程系统管理接口
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

        //不需要获取同步的monitor和synchronizer信息，仅仅获取线程和线程堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);

        //遍历线程信息，仅打印线程ID和线程名称信息
        for( ThreadInfo threadInfo : threadInfos) {
            System.out.println("[" + threadInfo.getThreadId() + "] " + threadInfo.getThreadName());
        }
    }
}
