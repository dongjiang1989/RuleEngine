package com.huawei.rtc.rule.direct

import org.apache.camel.builder.RouteBuilder

class DirectConsumer2 extends RouteBuilder {
    @Override
    void configure() throws Exception {
        from("direct:chain2").log("DirectConsumer2 Camel body: " + body())
    }
}