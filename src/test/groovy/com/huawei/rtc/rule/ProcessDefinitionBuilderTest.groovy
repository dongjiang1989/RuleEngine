package com.huawei.rtc.rule

import org.apache.camel.model.RouteDefinition
import org.junit.Test

/**
 * Created by dongjiang on 2019/5/24.
 */
class ProcessDefinitionBuilderTest {
    def shouldFail = new GroovyTestCase().&shouldFail

    @Test
    public void shouldThrowWhenNoSuchMethod() {
        def builder = new ProcessorDefinitionBuilder(new RouteDefinition())

        shouldFail(MissingMethodException) {
            builder.noSuchMethod()
        }
    }

    @Test
    public void wouldCallMethodMissingWhenNoSuchMethod() {
        def delegate = new RouteDefinition()
        delegate.metaClass.methodMissing = { String name, args ->
            println "dongjiang!!!"
        }

        def builder = new ProcessorDefinitionBuilder(delegate)
        builder.noSuchMethod()
    }
}
