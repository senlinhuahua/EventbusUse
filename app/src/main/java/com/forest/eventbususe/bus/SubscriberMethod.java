package com.forest.eventbususe.bus;

import java.lang.reflect.Method;

/**
 * Created by forest on 2018/9/13 0013.
 */

public class SubscriberMethod {

    private Class<?> type;
    private Method method;
    private ThreadMode threadMode;

    public SubscriberMethod(Class<?> type, Method method, ThreadMode threadMode) {
        this.type = type;
        this.method = method;
        this.threadMode = threadMode;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public Method getMethod() {
        return method;
    }

    public ThreadMode getThreadMode() {
        return threadMode;
    }

    public void setThreadMode(ThreadMode threadMode) {
        this.threadMode = threadMode;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
