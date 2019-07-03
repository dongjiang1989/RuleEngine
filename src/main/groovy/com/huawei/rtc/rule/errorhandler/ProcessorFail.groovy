package com.huawei.rtc.rule.errorhandler


import org.apache.camel.Exchange
import org.apache.camel.Processor

class ProcessorFail implements Processor{
    @Override
    void process(Exchange exchange) throws Exception {
        throw new NullPointerException()
    }
}