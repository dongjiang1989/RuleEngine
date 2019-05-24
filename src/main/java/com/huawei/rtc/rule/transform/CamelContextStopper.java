package com.huawei.rtc.rule.transform;

import org.apache.camel.CamelContext;

/**
 * Created by dongjiang on 2019/5/24.
 */
public class CamelContextStopper implements Runnable {
    
    private CamelContext context;

    /**
     * Use {@link #registerToShutdownHook(CamelContext)} instead.
     */
    CamelContextStopper(final CamelContext context) {
        this.context = context;
    }

    /**
     * Calls {@link CamelContext#stop()}.
     *
     * @throws RuntimeException to wrap exception (if) thrown by {@link CamelContext#stop()}
     */
    public void run() {
        try {
            context.stop();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void registerToShutdownHook(final CamelContext context) {
        Runtime.getRuntime().addShutdownHook(new Thread(new CamelContextStopper(context)));
    }
}
