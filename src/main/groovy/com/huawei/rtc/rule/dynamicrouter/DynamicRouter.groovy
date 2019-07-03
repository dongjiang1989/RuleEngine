package com.huawei.rtc.rule.dynamicrouter

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by Tu Pham Phuong - phamptu@gmail.com on 10/29/18.*/
class DynamicRouter extends RouteBuilder {

    @Override
    void configure() throws Exception {

        from("file:data/inbox?noop=true").split().tokenize("\n").dynamicRouter(method(DynamicRouterBean.class, "route"))

        from("direct:route1").process(new Processor() {
            void process(Exchange exchange) {
                String body = exchange.getIn().getBody().toString()
                body = body + " in route 1";
                System.out.println(body);
                exchange.getIn().setBody(body)
            }
        });

        from("direct:route2").process(new Processor() {
            void process(Exchange exchange) {
                String body = exchange.getIn().getBody().toString()
                body = body + " in route 2"
                System.out.println(body)
                exchange.getIn().setBody(body)
            }
        });

        from("direct:route3").process(new Processor() {
            void process(Exchange exchange) {
                String body = exchange.getIn().getBody().toString();
                body = body + " in route 3"
                exchange.getIn().setBody(body)
                System.out.println(body)
            }
        })
    }
}
