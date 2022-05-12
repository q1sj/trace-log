package com.q1sj.log.demo;

import com.q1sj.log.core.TracePostZuulFilter;
import com.q1sj.log.core.TracePreZuulFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * @author Q1sj
 * @date 2022.5.12 13:36
 */
@EnableZuulProxy
@SpringBootApplication
public class ZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }
    @Bean
    public TracePreZuulFilter tracePreZuulFilter(){
        return new TracePreZuulFilter();
    }
    @Bean
    public TracePostZuulFilter tracePostZuulFilter(){
        return new TracePostZuulFilter();
    }
}
