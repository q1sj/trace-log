package com.q1sj.log.demo;

import com.q1sj.log.core.TraceHandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Q1sj
 * @date 2022.5.5 9:57
 */
//@Configuration
public class TraceHandlerInterceptorWebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 所有接口日志都增加traceId
        registry.addInterceptor(new TraceHandlerInterceptor()).addPathPatterns("/**");
    }
}
