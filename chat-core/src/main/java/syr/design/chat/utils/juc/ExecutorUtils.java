package syr.design.chat.utils.juc;

import java.util.concurrent.*;

public class ExecutorUtils {

    private static ExecutorUtils self;

    private static ExecutorService pool;

    private static ScheduledExecutorService scheduPool;


    static {
        int coreThreadSize = 5;
        self = new ExecutorUtils();
        int maxRunnableSize = 30;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(maxRunnableSize);
        int maxThreadSize = 10;
        int keepAliveTime = 1800;

        //几种线程池初始化
        pool = new ThreadPoolExecutor(coreThreadSize,
                maxThreadSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                workQueue,
                new ThreadFactory(),
                (r, executor) -> {
                    throw new RejectedExecutionException(r.toString() + executor.toString());
                });
//        pool = Executors.newCachedThreasdPool();
//        pool = Executors.newFixedThreadPool(maxThreadSize);
//        pool = Executors.newSingleThreadExecutor();
        System.out.println("[shangyouren]---------------------------EXECUTORUTILS");

        scheduPool = new ScheduledThreadPoolExecutor(coreThreadSize, new ThreadFactory(),
                (r, executor) -> {
                    throw new RejectedExecutionException(r.toString() + executor.toString());
                });
    }

    private ExecutorUtils(){}

    public static ExecutorUtils getExecutor(){
        return self;
    }

    public static ExecutorService getPool(){
        return pool;
    }

    public static ScheduledExecutorService getScheduPool(){
        return scheduPool;
    }

}
