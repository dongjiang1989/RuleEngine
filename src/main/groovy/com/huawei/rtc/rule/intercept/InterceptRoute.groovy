package com.huawei.rtc.rule.intercept

import org.apache.camel.Exchange
import org.apache.camel.Processor
import org.apache.camel.builder.RouteBuilder

class InterceptRoute extends RouteBuilder {
    int count

    @Override
    void configure() throws Exception {
        intercept().process(new Processor() {
            void process(Exchange exchange) {
                count++;
                System.out.println("interceptor called " + count + " times " + exchange.getIn().getBody())

            }
        })
        from("file:data/inbox?noop=true").split().tokenize("\n")
                .to("direct:javainuse1")
                .to("direct:javainuse2")
    }

}
