package com.huawei.rtc.rule

import org.apache.camel.model.ChoiceDefinition;
/**
 * Created by dongjiang on 2019/5/24.
 */
class ChoiceDefinitionBuilder {
    def ChoiceDefinition currentDelegate

    ChoiceDefinitionBuilder(ChoiceDefinition initialDelegate) {
        this.currentDelegate = initialDelegate
    }

    Object invokeMethod(String name, Object args) {
        def mc = currentDelegate.metaClass

        def method = mc.getMetaMethod(name, args)
        if (method == null) {
            return mc.invokeMissingMethod(currentDelegate, name, args)
        }

        def result = method.doMethodInvoke(currentDelegate, args)
        if (result instanceof ChoiceDefinition) {
            // Builder method called
            currentDelegate = result
            return this
        }

        // Non-builder method called
        return result
    }

}
