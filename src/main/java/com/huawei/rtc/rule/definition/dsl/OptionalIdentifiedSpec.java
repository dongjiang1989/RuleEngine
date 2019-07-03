package com.huawei.rtc.rule.definition.dsl;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.OptionalIdentifiedDefinition;

public class OptionalIdentifiedSpec<T extends OptionalIdentifiedDefinition<T>> {

    protected RouteBuilder routeBuilder;
    protected T definition;

    public OptionalIdentifiedSpec(T definition, RouteBuilder routeBuilder) {
        this.definition = definition;
        this.routeBuilder = routeBuilder;
    }

    public T getDefinition() {
        return definition;
    }

    public void id(String id) {
        definition = definition.id(id);
    }

    public void description(String text) {
        definition = definition.description(text);
    }

    public void description(String id, String text, String lang) {
        definition = definition.description(id, text, lang);
    }
}
