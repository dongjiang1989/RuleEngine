package com.huawei.rtc.rule.time

import org.apache.camel.builder.RouteBuilder

class TimerToLogRoute extends RouteBuilder {
    private final String TIMER_ROUTE = "TIMER_ROUTE"

    @Override
    void configure() throws Exception {
        log.info("TimerToLogController configure()")
        from("timer:initial//start?period=60000")
                .routeId(TIMER_ROUTE)
                .to("log:LogCategoryHere?level=WARN&showAll=true&multiline=true")
    }
}
