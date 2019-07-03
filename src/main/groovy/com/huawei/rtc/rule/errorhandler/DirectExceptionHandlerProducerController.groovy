package com.huawei.rtc.rule.errorhandler

import groovy.util.logging.Slf4j
import org.apache.camel.ProducerTemplate


@Slf4j
class DirectExceptionHandlerProducerController {
    ProducerTemplate producerTemplate

    void get() {
        log.info("DirectExceptionHandlerProducerController get")
        producerTemplate.sendBody("direct:exception_handler", "Calling via Spring Boot Rest Controller")
    }
}
