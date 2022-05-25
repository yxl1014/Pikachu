package yxl;

import java.util.Date;

public class Test {
    public static void main(String[] args) {
        Worker worker = new Worker();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //worker.schedule(this, 3000);
                System.out.println("执行任务的时间：" + new Date());
            }
        };
        System.out.println("添加任务的时间：" + new Date());
        worker.schedule(runnable, 3000);
        worker.schedule(runnable, 5000);
        worker.schedule(runnable, 6000);
        worker.schedule(runnable, 1000);
        worker.schedule(runnable, 2000);
        worker.schedule(runnable, 9000);
    }
}
