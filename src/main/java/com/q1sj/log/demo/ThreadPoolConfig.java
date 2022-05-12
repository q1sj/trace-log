package com.q1sj.log.demo;

import com.q1sj.log.core.TraceExecutorServiceImpl;
import org.springframework.aop.interceptor.AsyncExecutionAspectSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Q1sj
 * @date 2022.5.6 13:43
 */
@EnableAsync
@Configuration
public class ThreadPoolConfig {
    @Bean(AsyncExecutionAspectSupport.DEFAULT_TASK_EXECUTOR_BEAN_NAME)
    public ExecutorService traceThreadPool() {
        ExecutorService executorService = Executors.newFixedThreadPool(16);
        return new TraceExecutorServiceImpl(executorService);
    }
}
