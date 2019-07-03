package com.huawei.rtc.rule

import groovy.transform.CompileStatic
import org.apache.camel.EndpointInject
import org.apache.camel.RoutesBuilder
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.component.mock.MockEndpoint
import org.apache.camel.test.junit4.CamelTestSupport
import org.junit.Test

class RouteBuilderTest extends CamelTestSupport {
    @EndpointInject(uri = 'mock:out')
    MockEndpoint mockEndpoint

    @CompileStatic
    protected RoutesBuilder createRouteBuilder() throws Exception {
        new RouteBuilder() {
            @Override
            void configure() throws Exception {
                onException(RuntimeException).handled(true)

                from ('direct:input').routeId('myDslRoute').choice().
                        when(body().isEqualTo(constant('Hi'))).log("hahahaha").to('mock:out').otherwise().to('mock:out').endChoice()
                }
            }
        }


    @Test
    void 'test route'() {
        mockEndpoint.expectedMessageCount 1
        template.sendBody 'direct:input', 'Hi'
        assertMockEndpointsSatisfied()
    }
}

