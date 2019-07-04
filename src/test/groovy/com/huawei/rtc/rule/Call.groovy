package com.huawei.rtc.rule

import groovy.transform.CompileStatic
import org.apache.camel.EndpointInject
import com.huawei.rtc.rule.errorhandler.ProcessorSuccess;
import org.apache.camel.RoutesBuilder
import org.apache.camel.Exchange
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.component.mock.MockEndpoint
import org.apache.camel.test.junit4.CamelTestSupport
import org.junit.Test

/**
 * Created by dongjiang on 2019/7/4.
 */
class Call extends CamelTestSupport {
    @EndpointInject(uri = 'mock:out')
    MockEndpoint mockEndpoint

    @CompileStatic
    protected RoutesBuilder createRouteBuilder() throws Exception {
        new RouteBuilder() {
            @Override
            void configure() throws Exception {
                onException(RuntimeException).handled(true)

                from ('direct:input').choice().
                        when(body().isEqualTo(constant('aabb')))
                        .process(new ProcessorSuccess())
                        .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                        .setHeader(Exchange.HTTP_QUERY, constant("wd="+body().toString()))
                        .to("ahc:http://www.baidu.com")
                        .to("mock:out")
                        .otherwise()
                        .to("mock:results")
                        .endChoice()
            }
        }
    }


    @Test
    void 'test ahc'() {
        mockEndpoint.expectedMessageCount 1
        template.sendBody 'direct:input', 'aabb'
        assertMockEndpointsSatisfied()
    }
}