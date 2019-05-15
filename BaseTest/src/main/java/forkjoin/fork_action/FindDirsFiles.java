package forkjoin.fork_action;

import tools.SleepTools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 *类说明：遍历指定目录（含子目录）找寻指定类型文件
 */
public class FindDirsFiles extends RecursiveAction {

    private File path;

    public FindDirsFiles(File path) {
        this.path = path;
    }

    @Override
    protected void compute() {
        List<FindDirsFiles> subTasks = new ArrayList<>();

        File[] files = path.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    subTasks.add(new FindDirsFiles(file));
                } else {
                    if (file.getAbsolutePath().endsWith("txt")) {
                        System.out.println("文件：" + file.getAbsolutePath());
                    }
                }
            }
        }
        if (!subTasks.isEmpty()) {
            for (FindDirsFiles subTask : invokeAll(subTasks)) {
                subTask.join();
            }
        }
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        FindDirsFiles task = new FindDirsFiles(new File("f:/"));

        /* 异步 */
        pool.execute(task);

        System.out.println("Task is Running ....");
        SleepTools.ms(1);
        int otherWork = 0;
        for (int i = 0; i < 100; i++) {
            otherWork = otherWork + 1;
        }
        System.out.println("Main Thread done sth... otherWork=" + otherWork);
        task.join();//阻塞方法
        System.out.println("Task end");


    }
}
