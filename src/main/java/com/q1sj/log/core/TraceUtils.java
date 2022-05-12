package com.q1sj.log.core;

import org.slf4j.MDC;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * @author Q1sj
 * @date 2022.5.7 15:20
 */
public class TraceUtils {

    public static final String MDC_TRACEE_ID_KEY = "trace-id";

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
     * 向 {@link MDC}中添加追踪id
     * 同一线程只有第一次put会成功,调用put成功才应该在结束时removeId
     * 否在 在嵌套调用时内部方法可能提前清理traceId
     *
     * @return 是否put成功
     */
    protected static boolean putId() {
        // 防止同一链路 多次调用修改id
        String id = MDC.get(MDC_TRACEE_ID_KEY);
        if (id != null) {
            return false;
        }
        id = nextId();
        MDC.put(MDC_TRACEE_ID_KEY, id);
        return true;
    }

    /**
     * 移除{@link MDC}中的追踪id
     */
    protected static void removeId() {
        MDC.remove(MDC_TRACEE_ID_KEY);
    }


    protected static String nextId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
