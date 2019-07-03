package com.huawei.rtc.rule.direct

import org.apache.camel.builder.RouteBuilder

class DirectConsumer extends RouteBuilder {
    @Override
    void configure() throws Exception {
        from("direct:chain")
                .log("DirectConsumer Camel body: " + body())
                .to("direct:chain2")
    }
}