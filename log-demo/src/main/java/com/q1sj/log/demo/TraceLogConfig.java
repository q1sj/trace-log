package com.q1sj.log.demo;

import com.q1sj.log.core.TraceAop;
import com.q1sj.log.core.TraceFeignRequestInterceptor;
import com.q1sj.log.core.TraceHandlerInterceptor;
import com.q1sj.log.core.TraceThreadPoolTaskExecutor;
import org.springframework.aop.interceptor.AsyncExecutionAspectSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Q1sj
 * @date 2022.5.9 11:26
 */
@EnableAsync
@Configuration
public class TraceLogConfig implements WebMvcConfigurer {
    @Bean
    public TraceAop traceAop() {
        return TraceAop.INSTANCE;
    }

    @Bean
    public TraceFeignRequestInterceptor traceFeignRequestInterceptor(){
        return new TraceFeignRequestInterceptor();
    }

    @Bean(AsyncExecutionAspectSupport.DEFAULT_TASK_EXECUTOR_BEAN_NAME)
    public ThreadPoolTaskExecutor traceThreadPool() {
        // 修改@Async 默认线程池
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new TraceThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setThreadNamePrefix("traceExecutor-");
        threadPoolTaskExecutor.setMaxPoolSize(16);
        return threadPoolTaskExecutor;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 所有接口日志都增加traceId
        registry.addInterceptor(new TraceHandlerInterceptor()).addPathPatterns("/**");
    }
}
