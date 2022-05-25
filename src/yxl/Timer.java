package yxl;


import java.util.concurrent.PriorityBlockingQueue;

/**
 * 学习使用了以下网址，是Java本身Timer的简单实现。
 * Learn to use the following URL, which is a simple implementation of Java timer itself.
 * https://blog.csdn.net/wkh18891843165/article/details/115253515
 */
public class Timer implements Comparable<Timer> {
    long time;
    Runnable runnable;

    public Timer(Runnable runnable, long after) {
        this.runnable = runnable;
        this.time = System.currentTimeMillis() + after;
    }

    public void run() {
        runnable.run();
    }


    @Override
    public int compareTo(Timer o) {
        return (int) (this.time - o.time);
    }
}

class Worker {
    PriorityBlockingQueue<Timer> priorityBlockingQueue = new PriorityBlockingQueue<>();
    Object lock = new Object();
    long timeout = 1000 * 60;

    public Worker() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Timer timer = priorityBlockingQueue.take();
                    if (timer.time <= System.currentTimeMillis()) {
                        timer.run();
                    } else {
                        priorityBlockingQueue.put(timer);
                        System.out.println("暂时没有任务执行");
                        synchronized (lock) {
                            System.out.println("wait--->" + (timer.time - System.currentTimeMillis()));
                            lock.wait(timer.time - System.currentTimeMillis());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void schedule(Runnable runnable, long after) {
        priorityBlockingQueue.put(new Timer(runnable, after));
        synchronized (lock) {
            lock.notify();
        }
    }
}