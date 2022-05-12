package com.q1sj.log.core;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author Q1sj
 * @date 2022.5.7 15:15
 */
@Aspect
public class TraceAop {

    public static final TraceAop INSTANCE = new TraceAop();

    private TraceAop(){}

    @Around("@annotation(com.q1sj.log.core.Trace)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        boolean putSuccess = TraceUtils.putId();
        try {
            return pjp.proceed();
        } finally {
            if (putSuccess){
                TraceUtils.removeId();
            }
        }
    }

}
