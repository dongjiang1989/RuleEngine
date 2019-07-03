package com.huawei.rtc.rule.common;

import groovy.lang.Closure;

/**
 * Created by dongjiang on 2019/7/2.
 */
public class ClosureUtils {

    public static void call(Closure<?> closure, Object delegate, Object owner, Object thisObject) {
        Closure<?> code = closure.rehydrate(delegate, owner, thisObject);
        code.setResolveStrategy(Closure.DELEGATE_FIRST);
        code.call();
    }

}
