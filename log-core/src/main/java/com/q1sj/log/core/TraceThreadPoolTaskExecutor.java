package com.q1sj.log.core;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author Q1sj
 * @date 2022.5.9 17:20
 */
public class TraceThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {
    @Override
    public void execute(Runnable task) {
        super.execute(TraceUtils.packing(task));
    }

    @Override
    public void execute(Runnable task, long startTimeout) {
        super.execute(TraceUtils.packing(task), startTimeout);
    }

    @Override
    protected void cancelRemainingTask(Runnable task) {
        super.cancelRemainingTask(task);
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(TraceUtils.packing(task));
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return super.submit(TraceUtils.packing(task));
    }

    @Override
    public ListenableFuture<?> submitListenable(Runnable task) {
        return super.submitListenable(TraceUtils.packing(task));
    }

    @Override
    public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
        return super.submitListenable(TraceUtils.packing(task));
    }
}
