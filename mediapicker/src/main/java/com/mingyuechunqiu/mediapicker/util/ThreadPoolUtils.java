package com.mingyuechunqiu.mediapicker.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/4/25
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class ThreadPoolUtils {

    private static ExecutorService sExecutorService;

    /**
     * 执行一个任务
     *
     * @param runnable 任务执行体
     */
    public static void executeAction(Runnable runnable) {
        if (sExecutorService != null && sExecutorService.isShutdown()) {
            sExecutorService = null;
        }
        getCachedThreadPools();
        sExecutorService.submit(runnable);
    }

    /**
     * 停止线程池执行
     *
     * @param isNow 是否立即停止
     */
    public static void shutDown(boolean isNow) {
        if (sExecutorService == null || sExecutorService.isShutdown()) {
            return;
        }
        if (isNow) {
            sExecutorService.shutdownNow();
        } else {
            sExecutorService.shutdown();
        }
    }

    /**
     * 创建缓存线程池
     *
     * @return 返回调度器
     */
    private static ExecutorService getCachedThreadPools() {
        if (sExecutorService == null) {
            synchronized (ThreadPoolUtils.class) {
                if (sExecutorService == null) {
                    sExecutorService = Executors.newCachedThreadPool();
                }
            }
        }
        return sExecutorService;
    }
}
