package com.q1sj.log.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * @author Q1sj
 * @date 2022.5.7 15:20
 */
public class TraceUtils {

    protected static final String MDC_TRACE_ID_KEY = "trace-id";

    private static final Logger log = LoggerFactory.getLogger(TraceUtils.class);

    /**
     * 包装runnable对象 拷贝MDC中内容到子线程
     *
     * @param runnable
     * @return
     */
    public static Runnable packing(Runnable runnable) {
        Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
        return () -> {
            MDC.setContextMap(copyOfContextMap);
            try {
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }

    /**
     * 包装callable对象 拷贝MDC中内容到子线程
     *
     * @param callable
     * @param <T>
     * @return
     */
    public static <T> Callable<T> packing(Callable<T> callable) {
        Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
        return () -> {
            MDC.setContextMap(copyOfContextMap);
            try {
                return callable.call();
            } finally {
                MDC.clear();
            }
        };
    }

    /**
     * @return 当前线程 traceId
     */
    public static String currentId() {
        return MDC.get(MDC_TRACE_ID_KEY);
    }

    /**
     * 向 {@link MDC}中添加追踪id
     * 同一线程只有第一次put会成功,调用put成功才应该在结束时removeId
     * 否在 在嵌套调用时内部方法可能提前清理traceId
     *
     * @return 是否put成功
     */
    protected static boolean putId() {
        return putId(nextId());
    }

    protected static boolean putId(String traceId) {
        // 防止同一链路 多次调用修改id
        if (MDC.get(MDC_TRACE_ID_KEY) != null) {
            return false;
        }
        MDC.put(MDC_TRACE_ID_KEY, traceId);
        log.debug("put {}", MDC_TRACE_ID_KEY);
        return true;
    }

    /**
     * 移除{@link MDC}中的追踪id
     */
    protected static void removeId() {
        log.debug("remove {}", MDC_TRACE_ID_KEY);
        MDC.remove(MDC_TRACE_ID_KEY);
    }


    protected static String nextId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
