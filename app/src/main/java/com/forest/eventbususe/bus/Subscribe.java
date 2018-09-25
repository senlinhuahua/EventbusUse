package com.forest.eventbususe.bus;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by forest on 2018/9/13 0013.
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface Subscribe {
    ThreadMode threadMode()default ThreadMode.POSTING;
}
