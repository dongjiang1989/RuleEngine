package com.huawei.rtc.rule

import org.apache.camel.CamelContext
import org.apache.camel.impl.DefaultCamelContext
import org.junit.Test
/**
 * Created by dongjiang on 2019/5/24.
 */
@Mixin(CamelEngine)
class CamelEngineTest {
    private CamelContext camelContext = new DefaultCamelContext()

    @Test
    void 'should be able to pass closure in as RouteBuilder'() {
        final def expectedResult = 'Result'

        routes {
            from('direct:input').transform(constant(expectedResult))
        }

        def result = camelContext.createProducerTemplate().requestBody('direct:input', (Object) null)

        assert result == expectedResult
    }
}
