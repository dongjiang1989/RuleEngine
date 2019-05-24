package com.huawei.rtc.rule.transform;

import groovy.grape.Grape;
import org.apache.camel.CamelContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dongjiang on 2019/5/24.
 */
public class CamelDependencyGrabber {
    private String camelVersion;

    /**
     * @param camelContext to get the {@link CamelContext#getVersion() version}
     */
    public CamelDependencyGrabber(CamelContext camelContext) {
        this.camelVersion = camelContext.getVersion();
    }

    /**
     * Grab specified camel modules.
     *
     * @param moduleNames e.g. {@code jetty} for {@code camel-jetty}, {@code twitter} for {@code camel-twitter},
     * and so on
     * @see Grape#grab(java.util.Map, java.util.Map...)
     */
    public void require(String... moduleNames) {
        List<Map<String, Object>> dependencies = new ArrayList<Map<String, Object>>();

        for (String moduleName : moduleNames) {
            Map<String, Object> artifact = new HashMap<String, Object>();
            artifact.put("group", "org.apache.camel");
            artifact.put("module", "camel-" + moduleName);
            artifact.put("version", camelVersion);

            dependencies.add(artifact);
        }

        Grape.grab(new HashMap<String, Object>(), dependencies.toArray(new Map[dependencies.size()]));
    }
}
