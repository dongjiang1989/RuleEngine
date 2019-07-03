package com.huawei.rtc.rule.errorhandler


import org.apache.camel.builder.RouteBuilder

class ErrorHandlerRoute extends RouteBuilder {
    @Override
    void configure() throws Exception {
        from("direct:exception_handler")
                .doTry()
                .process(new ProcessorFail())
                .to("log:LogCategoryHere?level=WARN&showAll=true&multiline=true")
                .doCatch(Exception.class)
                .to("log:LogCategoryHere?level=WARN&showAll=true&multiline=true")
                .to("mock:error")
                .doFinally()
                .to("mock:end")
    }
}
