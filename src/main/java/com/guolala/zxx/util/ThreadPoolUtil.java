package com.guolala.zxx.util;

import java.util.concurrent.*;

public class ThreadPoolUtil {

    private static final int CORE_POOL_SIZE = 20;
    private static final int MAX_POOL_SIZE = 50;
    private static final long KEEP_ALIVE_TIME = 0L;



    private final ExecutorService executor;

    private static ThreadPoolUtil instance = new ThreadPoolUtil();

    private ThreadPoolUtil() {
        this.executor = new ThreadPoolExecutor(CORE_POOL_SIZE,MAX_POOL_SIZE,KEEP_ALIVE_TIME,TimeUnit.SECONDS,new LinkedBlockingQueue<>(),Executors.defaultThreadFactory());
    }

    public static ThreadPoolUtil getInstance() {
        return instance;
    }

    public static <T> Future<T> execute(final Callable<T> runnable) {
        return getInstance().executor.submit(runnable);
    }

    public static Future<?> execute(final Runnable runnable) {
        return getInstance().executor.submit(runnable);
    }
}
