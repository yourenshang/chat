package syr.design.chat.utils.juc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.annotation.PreDestroy;
import java.util.concurrent.*;

/**
 * @author shangyouren
 * @since 2019-07-02
 */
@Slf4j
@Component
public class ExecutorUtils {

    private ExecutorService pool;

    private ScheduledExecutorService scheduPool;

    public ExecutorUtils() {
        int coreThreadSize = 5;
        int maxRunnableSize = 30;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(maxRunnableSize);
        int maxThreadSize = 10;
        int keepAliveTime = 1800;
        pool = new ThreadPoolExecutor(coreThreadSize,
                maxThreadSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                workQueue,
                Thread::new,
                (r, executor) -> {
                    throw new RejectedExecutionException("线程池已满:" + r.toString() + executor.toString());
                });
        scheduPool = new ScheduledThreadPoolExecutor(coreThreadSize, Thread::new,
                (r, executor) -> {
                    throw new RejectedExecutionException("线程池已满:" + r.toString() + executor.toString());
                });
        log.info("ExecutorUtils new: OK");
    }

    @Deprecated
    private ExecutorUtils(int threads) {
        pool = Executors.newFixedThreadPool(threads);
        pool = Executors.newSingleThreadExecutor();
    }

    public void submit(Runnable r){
        if (r != null){
            pool.submit(r);
        }
    }

    public void submitSchedu(Runnable r, long time){
        if (r != null){
            scheduPool.schedule(r, time, TimeUnit.SECONDS);
        }
    }

    @PreDestroy
    public void shutdown(){
        log.info("ExecutorUtils shutdown: close ...");
        pool.shutdown();
        scheduPool.shutdown();
        log.info("ExecutorUtils shutdown: ok");
    }

}
