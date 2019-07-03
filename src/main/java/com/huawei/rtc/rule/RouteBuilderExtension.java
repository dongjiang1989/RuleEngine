package com.huawei.rtc.rule;

import groovy.lang.Closure;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;

/**
 * Created by dongjiang on 2019/5/24.
 */
public class RouteBuilderExtension {
    /**
     * Chaining method calls to build route can get pretty unreadable or error prone:
     * <pre>
     * from('...').to('...').process {...}.transform(constant('Result')).process {...}.to('...')
     * from 'direct:input' to '...' process {...} transform(constant('Result')) process {...}.to('...')
     * </pre>
     * This enables an alternative:
     * <pre>
     * from 'direct:input' {
     *      to '...'
     *      process {...}
     *      transform constant('Result')
     *      process {...}
     *      to('...')
     * }
     * </pre>
     * @param self to start the route
     * @param uri the 'from' URI
     * @param defineRoutePrototype route definition after 'from'
     */
    public static void from(final RouteBuilder self, final String uri, final Closure defineRoutePrototype) {
        Closure defineRoute = (Closure) defineRoutePrototype.clone();

        RouteDefinition routeDefinition = self.from(uri);
        defineRoute.setDelegate(new ProcessorDefinitionBuilder(routeDefinition));
        defineRoute.setResolveStrategy(Closure.DELEGATE_FIRST);

        defineRoute.call();
    }
}
