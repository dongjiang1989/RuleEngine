package com.huawei.rtc.rule.common;

import com.huawei.rtc.rule.call.CallEndpoint;
import com.ning.http.client.HttpResponseHeaders;
import com.ning.http.client.HttpResponseStatus;
import com.ning.http.client.Request;
import org.apache.camel.Exchange;

import java.io.ByteArrayOutputStream;

/**
 * Created by dongjiang on 2019/5/25.
 */
public interface Binding {

    /**
     * Prepares the AHC {@link Request} to be send.
     *
     * @param endpoint the endpoint
     * @param exchange the exchange
     * @return the request to send using the {@link com.ning.http.client.AsyncHttpClient}
     * @throws Exception is thrown if error occurred preparing the request
     */
    Request prepareRequest(CallEndpoint endpoint, Exchange exchange) throws Exception;

    /**
     * Callback from the {@link com.ning.http.client.AsyncHttpClient} when an exception occurred sending the request.
     *
     * @param endpoint the endpoint
     * @param exchange the exchange
     * @param t        the thrown exception
     * @throws Exception is thrown if error occurred in the callback
     */
    void onThrowable(CallEndpoint endpoint, Exchange exchange, Throwable t) throws Exception;

    /**
     * Callback from the {@link com.ning.http.client.AsyncHttpClient} when the HTTP response status was received
     *
     * @param endpoint       the endpoint
     * @param exchange       the exchange
     * @param responseStatus the HTTP response status
     * @throws Exception is thrown if error occurred in the callback
     */
    void onStatusReceived(CallEndpoint endpoint, Exchange exchange, HttpResponseStatus responseStatus) throws Exception;

    /**
     * Callback from the {@link com.ning.http.client.AsyncHttpClient} when the HTTP headers was received
     *
     * @param endpoint the endpoint
     * @param exchange the exchange
     * @param headers  the HTTP headers
     * @throws Exception is thrown if error occurred in the callback
     */
    void onHeadersReceived(CallEndpoint endpoint, Exchange exchange, HttpResponseHeaders headers) throws Exception;

    /**
     * Callback from the {@link com.ning.http.client.AsyncHttpClient} when complete and all the response has been received.
     *
     *
     * @param endpoint      the endpoint
     * @param exchange      the exchange
     * @param url           the url requested
     * @param os            output stream with the HTTP response body
     * @param contentLength length of the response body
     * @param statusCode    the http response code
     * @param statusText    the http status text
     * @throws Exception is thrown if error occurred in the callback
     */
    void onComplete(CallEndpoint endpoint, Exchange exchange, String url, ByteArrayOutputStream os, int contentLength,
                    int statusCode, String statusText) throws Exception;
}
