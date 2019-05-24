package com.huawei.rtc.rule;

import org.apache.camel.Message;
import org.apache.camel.NoTypeConversionAvailableException;

/**
 * Created by dongjiang on 2019/5/24.
 */
public class MessageExtension {
    /**
     *
     * @param self
     * @param type
     * @return
     * @throws NoTypeConversionAvailableException
     */
    public static Object asType(final Message self, final Class type) throws NoTypeConversionAvailableException {
        Object convertedBody = self.getBody(type);
        if (convertedBody == null) {
            throw new NoTypeConversionAvailableException(self, type);
        }
        return convertedBody;
    }
}
