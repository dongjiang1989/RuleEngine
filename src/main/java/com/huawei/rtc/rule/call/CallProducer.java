package com.huawei.rtc.rule.call;

import com.ning.http.client.*;
import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultAsyncProducer;

import java.io.ByteArrayOutputStream;

/**
 * Created by dongjiang on 2019/5/25.
 */
public class CallProducer extends DefaultAsyncProducer {
    private final AsyncHttpClient client;

    public CallProducer(CallEndpoint endpoint) {
        super(endpoint);
        this.client = endpoint.getClient();
    }

    @Override
    public CallEndpoint getEndpoint() {
        return (CallEndpoint) super.getEndpoint();
    }

    @Override
    public boolean process(Exchange exchange, AsyncCallback callback) {
        try {
            // AHC supports async processing
            Request request = getEndpoint().getBinding().prepareRequest(getEndpoint(), exchange);
            log.debug("Executing request {} ", request);
            client.prepareRequest(request).execute(new CallAsyncHandler(exchange, callback, request.getUrl(), getEndpoint().getBufferSize()));
            return false;
        } catch (Exception e) {
            exchange.setException(e);
            callback.done(true);
            return true;
        }
    }

    /**
     * Camel {@link CallAsyncHandler} to receive callbacks during the processing of the request.
     */
    private final class CallAsyncHandler implements AsyncHandler<Exchange> {

        private final Exchange exchange;
        private final AsyncCallback callback;
        private final String url;
        private final ByteArrayOutputStream os;
        private int contentLength;
        private int statusCode;
        private String statusText;

        private CallAsyncHandler(Exchange exchange, AsyncCallback callback, String url, int bufferSize) {
            this.exchange = exchange;
            this.callback = callback;
            this.url = url;
            this.os = new ByteArrayOutputStream(bufferSize);
        }

        @Override
        public void onThrowable(Throwable t) {
            if (log.isTraceEnabled()) {
                log.trace("{} onThrowable {}", exchange.getExchangeId(), t);
            }
            try {
                getEndpoint().getBinding().onThrowable(getEndpoint(), exchange, t);
            } catch (Exception e) {
                exchange.setException(e);
            } finally {
                callback.done(false);
            }
        }

        @Override
        public STATE onBodyPartReceived(HttpResponseBodyPart bodyPart) throws Exception {
            // write body parts to stream, which we will bind to the Camel Exchange in onComplete
            int wrote = bodyPart.writeTo(os);
            if (log.isTraceEnabled()) {
                log.trace("{} onBodyPartReceived {} bytes", exchange.getExchangeId(), wrote);
            }
            contentLength += wrote;
            return STATE.CONTINUE;
        }

        @Override
        public STATE onStatusReceived(HttpResponseStatus responseStatus) throws Exception {
            if (log.isTraceEnabled()) {
                log.trace("{} onStatusReceived {}", exchange.getExchangeId(), responseStatus);
            }
            try {
                statusCode = responseStatus.getStatusCode();
                statusText = responseStatus.getStatusText();
                getEndpoint().getBinding().onStatusReceived(getEndpoint(), exchange, responseStatus);
            } catch (Exception e) {
                exchange.setException(e);
            }
            return STATE.CONTINUE;
        }

        @Override
        public STATE onHeadersReceived(HttpResponseHeaders headers) throws Exception {
            if (log.isTraceEnabled()) {
                log.trace("{} onHeadersReceived {}", exchange.getExchangeId(), headers);
            }
            try {
                getEndpoint().getBinding().onHeadersReceived(getEndpoint(), exchange, headers);
            } catch (Exception e) {
                exchange.setException(e);
            }
            return STATE.CONTINUE;
        }

        @Override
        public Exchange onCompleted() throws Exception {
            if (log.isTraceEnabled()) {
                log.trace("{} onCompleted", exchange.getExchangeId());
            }
            try {
                getEndpoint().getBinding().onComplete(getEndpoint(), exchange, url, os, contentLength, statusCode, statusText);
            } catch (Exception e) {
                exchange.setException(e);
            } finally {
                // signal we are done
                callback.done(false);
            }
            return exchange;
        }

        @Override
        public String toString() {
            return "AhcAsyncHandler for exchangeId: " + exchange.getExchangeId() + " -> " + url;
        }
    }
}
