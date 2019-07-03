package com.huawei.rtc.rule.definition.dsl;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import com.huawei.rtc.rule.common.ClosureUtils;
import org.apache.camel.model.ChoiceDefinition;

public class ChoiceSpec extends ProcessorSpec<ChoiceDefinition> {

    public ChoiceSpec(ChoiceDefinition definition, RouteBuilder routeBuilder) {
        super(definition, routeBuilder);
    }

    public void when(Predicate predicate, @DelegatesTo(ProcessorSpec.class)Closure<?> closure) {
        ProcessorSpec<ChoiceDefinition> choice = new ProcessorSpec<>(definition.when(predicate), routeBuilder);
        ClosureUtils.call(closure, choice, routeBuilder, routeBuilder);
        definition = choice.getDefinition().endChoice();
    }

    public void otherwise(@DelegatesTo(RouteSpec.class)Closure<?> closure) {
        ProcessorSpec<ChoiceDefinition> choice = new ProcessorSpec<>(definition.otherwise(), routeBuilder);
        ClosureUtils.call(closure, choice, routeBuilder, routeBuilder);
        definition = choice.getDefinition().endChoice();
    }

}

