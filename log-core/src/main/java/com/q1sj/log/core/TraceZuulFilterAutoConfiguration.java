package com.q1sj.log.core;

import com.netflix.zuul.ZuulFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Q1sj
 * @date 2022.8.8 14:48
 */
@Configuration
@ConditionalOnClass(ZuulFilter.class)
public class TraceZuulFilterAutoConfiguration {
    private final Logger log = LoggerFactory.getLogger(TraceLogAutoConfiguration.class);

    @Bean
    public TracePreZuulFilter tracePreZuulFilter() {
        log.debug("init tracePreZuulFilter");
        return new TracePreZuulFilter();
    }

    @Bean
    public TracePostZuulFilter tracePostZuulFilter() {
        log.debug("init tracePostZuulFilter");
        return new TracePostZuulFilter();
    }
}
