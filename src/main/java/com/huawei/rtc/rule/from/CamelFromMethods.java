package com.huawei.rtc.rule.from;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import org.apache.camel.Endpoint;
import org.apache.camel.builder.RouteBuilder;
import com.huawei.rtc.rule.definition.dsl.OnExceptionSpec;
import com.huawei.rtc.rule.definition.dsl.RouteSpec;
import com.huawei.rtc.rule.common.ClosureUtils;
import org.apache.camel.model.ProcessorDefinition;

public final class CamelFromMethods {

    private CamelFromMethods() {
        // Utility Class
    }

    public static ProcessorDefinition<?> from(RouteBuilder self, String uri,
                                              @DelegatesTo(RouteSpec.class) Closure<?> closure) {
        RouteSpec route = new RouteSpec(self.from(uri), self);
        ClosureUtils.call(closure, route, self, self);
        return route.getDefinition();
    }

    public static ProcessorDefinition<?> from(RouteBuilder self, Endpoint endpoint,
                                              @DelegatesTo(RouteSpec.class) Closure<?> closure) {
        RouteSpec route = new RouteSpec(self.from(endpoint), self);
        ClosureUtils.call(closure, route, self, self);
        return route.getDefinition();
    }

    public static ProcessorDefinition<?> from(RouteBuilder self, String[] uris,
                                              @DelegatesTo(RouteSpec.class) Closure<?> closure) {
        RouteSpec route = new RouteSpec(self.from(uris), self);
        ClosureUtils.call(closure, route, self, self);
        return route.getDefinition();
    }

    public static ProcessorDefinition<?> from(RouteBuilder self, Endpoint[] endpoints,
                                              @DelegatesTo(RouteSpec.class) Closure<?> closure) {
        RouteSpec route = new RouteSpec(self.from(endpoints), self);
        ClosureUtils.call(closure, route, self, self);
        return route.getDefinition();
    }

    public static ProcessorDefinition<?> onException(RouteBuilder self, Class<? extends Throwable> exceptionClass,
                                                     @DelegatesTo(OnExceptionSpec.class) Closure<?> closure) {
        OnExceptionSpec onException = new OnExceptionSpec(self.onException(exceptionClass), self);
        ClosureUtils.call(closure, onException, self, self);
        return onException.getDefinition();
    }

}
