package com.huawei.rtc.rule.common;

import org.apache.camel.CamelException;
import org.apache.camel.util.ObjectHelper;

import java.util.Map;

/**
 * Created by dongjiang on 2019/5/25.
 */
public class OperationFailedException extends CamelException {
    private static final long serialVersionUID = -6731281444593522633L;
    private final String url;
    private final String redirectLocation;
    private final int statusCode;
    private final String statusText;
    private final Map<String, String> responseHeaders;
    private final String responseBody;

    public OperationFailedException(String url, int statusCode, String statusText, String location, Map<String, String> responseHeaders, String responseBody) {
        super("HTTP operation failed invoking " + url + " with statusCode: " + statusCode + (location != null ? ", redirectLocation: " + location : ""));
        this.url = url;
        this.statusCode = statusCode;
        this.statusText = statusText;
        this.redirectLocation = location;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
    }

    public String getUrl() {
        return url;
    }

    public boolean isRedirectError() {
        return statusCode >= 300 && statusCode < 400;
    }

    public boolean hasRedirectLocation() {
        return ObjectHelper.isNotEmpty(redirectLocation);
    }

    public String getRedirectLocation() {
        return redirectLocation;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusText() {
        return statusText;
    }

    public Map<String, String> getResponseHeaders() {
        return responseHeaders;
    }

    public String getResponseBody() {
        return responseBody;
    }
}
