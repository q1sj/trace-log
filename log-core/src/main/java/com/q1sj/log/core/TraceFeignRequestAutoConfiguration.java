package com.q1sj.log.core;

import feign.RequestInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Q1sj
 * @date 2022.8.8 14:52
 */
@Configuration
@ConditionalOnClass(RequestInterceptor.class)
public class TraceFeignRequestAutoConfiguration {
    private final Logger log = LoggerFactory.getLogger(TraceLogAutoConfiguration.class);

    @Bean
    public TraceFeignRequestInterceptor traceFeignRequestInterceptor() {
        log.debug("init TraceFeignRequestInterceptor");
        return new TraceFeignRequestInterceptor();
    }
}
