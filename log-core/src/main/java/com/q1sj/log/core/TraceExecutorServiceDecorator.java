package com.q1sj.log.core;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 拷贝父线程追踪id线程池
 *
 * @author Q1sj
 * @date 2022.5.5 17:16
 */
public class TraceExecutorServiceDecorator implements ExecutorService {

    private final ExecutorService executorService;

    public TraceExecutorServiceDecorator(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void shutdown() {
        executorService.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return executorService.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return executorService.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return executorService.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return executorService.awaitTermination(timeout, unit);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return executorService.submit(TraceUtils.packing(task));
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return executorService.submit(TraceUtils.packing(task), result);
    }

    @Override
    public Future<?> submit(Runnable task) {
        return executorService.submit(TraceUtils.packing(task));
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        List<Callable<T>> collect = tasks.stream().map(TraceUtils::packing).collect(Collectors.toList());
        return this.executorService.invokeAll(collect);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        List<Callable<T>> collect = tasks.stream().map(TraceUtils::packing).collect(Collectors.toList());
        return this.executorService.invokeAll(collect, timeout, unit);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        List<Callable<T>> collect = tasks.stream().map(TraceUtils::packing).collect(Collectors.toList());
        return this.executorService.invokeAny(collect);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        List<Callable<T>> collect = tasks.stream().map(TraceUtils::packing).collect(Collectors.toList());
        return this.executorService.invokeAny(collect, timeout, unit);
    }

    @Override
    public void execute(Runnable command) {
        executorService.execute(TraceUtils.packing(command));
    }
}
