package com.q1sj.log.core;

import ch.qos.logback.classic.LoggerContext;
import com.ofpay.logback.TtlMdcListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncExecutionAspectSupport;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Q1sj
 * @date 2022.8.8 14:13
 */
@Configuration
public class TraceLogAutoConfiguration {
    private final Logger log = LoggerFactory.getLogger(TraceLogAutoConfiguration.class);

    @Bean
    @ConditionalOnMissingBean
    public TraceAop traceAop() {
        log.debug("init traceAop");
        return TraceAop.INSTANCE;
    }

    @Bean
    @ConditionalOnMissingBean
    public TraceWebMvcConfigurer traceWebMvcConfigurer() {
        log.debug("init TraceWebMvcConfigurer");
        return new TraceWebMvcConfigurer();
    }

    @Bean
    public TtlMdcListener ttlMdcListener() {
        TtlMdcListener ttlMdcListener = new TtlMdcListener();
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        ttlMdcListener.setContext(lc);
        ttlMdcListener.start();
        return ttlMdcListener;
    }

    public static class TraceWebMvcConfigurer implements WebMvcConfigurer {
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            // 所有接口日志都增加traceId
            registry.addInterceptor(new TraceHandlerInterceptor()).addPathPatterns("/**");
        }
    }
}
