package com.huawei.rtc.rule.definition.dsl;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;


public class RouteSpec extends ProcessorSpec<RouteDefinition> {

    public RouteSpec(RouteDefinition definition, RouteBuilder routeBuilder) {
        super(definition, routeBuilder);
    }

    public void routeId(String id) {
        definition = definition.routeId(id);
    }

}
