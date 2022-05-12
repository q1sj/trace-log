package com.q1sj.log.demo;

import com.q1sj.log.core.TraceAop;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Q1sj
 * @date 2022.5.9 11:26
 */
@Configuration
public class TraceAopConfig {
    @Bean
    public TraceAop traceAop() {
        return TraceAop.INSTANCE;
    }
}
