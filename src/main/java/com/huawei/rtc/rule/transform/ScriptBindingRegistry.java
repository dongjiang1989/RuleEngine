package com.huawei.rtc.rule.transform;


import groovy.lang.Binding;
import groovy.lang.Script;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Created by dongjiang on 2019/5/24.
 */
public class ScriptBindingRegistry {
    /**
     * @see #ScriptBindingRegistry(Script)
     */
    private Script script;

    /**
     * @param script just to get the {@link Binding}.
     * @see ScriptBindingRegistry
     */
    public ScriptBindingRegistry(final Script script) {
        this.script = script;
    }

    /**
     * @return service from {@link Binding}, {@code null} if not found
     */
    public Object lookup(final String name) {
        Binding binding = getBinding();

        return binding.hasVariable(name) ? binding.getVariable(name) : null;
    }

    /**
     * @see #lookup(String)
     */
    public Object lookupByName(final String name) {
        return lookup(name);
    }

    private Binding getBinding() {
        return script.getBinding();
    }

    /**
     * Delegates to {@link #lookup(String)}.
     */
    public <T> T lookup(final String name, final Class<T> type) {
        Object result = lookup(name);
        return type.cast(result);
    }

    /**
     * @see #lookup(String, Class)
     */
    public <T> T lookupByNameAndType(final String name, final Class<T> type) {
        return lookup(name, type);
    }

    /**
     * @return services from {@link Binding} that are compatible (same type or subtype) with the given type, empty
     * {@code Map} if none found
     */
    public <T> Map<String, T> lookupByType(final Class<T> type) {
        Map<String, T> varName2Var = new HashMap<String, T>();
        for (Entry<String, ?> entry : (Set<Entry>) script.getBinding().getVariables().entrySet()) {
            Object value = entry.getValue();
            if (type.isInstance(value)) {
                varName2Var.put(entry.getKey(), type.cast(value));
            }
        }

        return varName2Var;
    }

    /**
     * @see #lookupByType(Class)
     */
    public <T> Map<String, T> findByTypeWithName(Class<T> type) {
        return lookupByType(type);
    }

    /**
     * @see #findByTypeWithName(Class)
     */
    public <T> Set<T> findByType(Class<T> type) {
        return new HashSet<T>(findByTypeWithName(type).values());
    }
}
