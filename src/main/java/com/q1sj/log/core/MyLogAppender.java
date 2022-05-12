package com.q1sj.log.core;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;

/**
 * @author Q1sj
 * @date 2022.4.29 9:18
 */
public class MyLogAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {
    @Override
    protected void append(ILoggingEvent eventObject) {
        String threadName = eventObject.getThreadName();
        String loggerName = eventObject.getLoggerName();
        Level level = eventObject.getLevel();
        String formattedMessage = eventObject.getFormattedMessage();
        long timeStamp = eventObject.getTimeStamp();
    }
}
