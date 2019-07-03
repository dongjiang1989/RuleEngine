package com.huawei.rtc.rule.definition.dsl;

import org.apache.camel.Expression;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.OnExceptionDefinition;

public class OnExceptionSpec extends ProcessorSpec<OnExceptionDefinition> {

    public OnExceptionSpec(OnExceptionDefinition definition, RouteBuilder routeBuilder) {
        super(definition, routeBuilder);
    }

    public void handled(boolean handled) {
        definition = definition.handled(handled);
    }

    public void handled(Predicate handled) {
        definition = definition.handled(handled);
    }

    public void handled(Expression handled) {
        definition = definition.handled(handled);
    }

}