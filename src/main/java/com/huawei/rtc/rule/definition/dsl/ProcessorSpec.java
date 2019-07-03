package com.huawei.rtc.rule.definition.dsl;

import com.huawei.rtc.rule.common.ClosureUtils;
import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ProcessorDefinition;

/**
 * @author sohrab
 */
public class ProcessorSpec<T extends ProcessorDefinition<T>> extends OptionalIdentifiedSpec<T> {

    public ProcessorSpec(T definition, RouteBuilder routeBuilder) {
        super(definition, routeBuilder);
    }

    public void to(String uri) {
        definition = definition.to(uri);
    }

    public void log(String message) {
        definition = definition.log(message);
    }

    public void choice(@DelegatesTo(ChoiceSpec.class) Closure<?> closure) {
        ChoiceSpec choice = new ChoiceSpec(definition.choice(), routeBuilder);
        ClosureUtils.call(closure, choice, routeBuilder, routeBuilder);
        this.definition = (T) choice.getDefinition().end();
    }

    public void throwException(Exception exception) {
        definition = definition.throwException(exception);
    }

    public void throwException(Class<? extends Exception> type, String message) {
        definition = definition.throwException(type, message);
    }

}
