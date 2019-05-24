package com.huawei.rtc.rule

import org.apache.camel.builder.RouteBuilder

/**
 * Created by dongjiang on 2019/5/24.
 */
@Category(Object)
class CamelEngine {
    /**
     * @param buildRoutePrototype things you'd do in {@link RouteBuilder#configure()}
     * @see org.apache.camel.CamelContext#addRoutes(org.apache.camel.RoutesBuilder)
     */
    def routes(Closure buildRoutePrototype) {
        RouteBuilder routeBuilder

        // Using closure because facing cryptic problem with anonymous class here
        routeBuilder = {
            // Should not need to clone as it's unlikely that this closure will be invoked in multiple threads
            // (or even multiple times), but I want to keep this as a reference/reminder
            Closure buildRoute = (Closure) buildRoutePrototype.clone();
            buildRoute.setDelegate(routeBuilder);

            buildRoute.call();
        }

        camelContext.addRoutes(routeBuilder);
        camelContext.start();
    }

    /**
     * Some routes do not block - e.g. 'file:' ends immediately after starting.
     * This is a convenience method to stop the script from exiting.
     */
    def waitForever() {
        Thread.currentThread().join()
    }
}
