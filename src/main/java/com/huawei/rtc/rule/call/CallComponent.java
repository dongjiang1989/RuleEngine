package com.huawei.rtc.rule.call;

import com.huawei.rtc.rule.common.Binding;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import org.apache.camel.Endpoint;
import org.apache.camel.impl.HeaderFilterStrategyComponent;
import org.apache.camel.util.jsse.SSLContextParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Map;

/**
 * Created by dongjiang on 2019/5/25.
 */
public class CallComponent extends HeaderFilterStrategyComponent {
    private static final Logger LOG = LoggerFactory.getLogger(CallComponent.class);

    private static final String CLIENT_CONFIG_PREFIX = "clientConfig.";
    private static final String CLIENT_REALM_CONFIG_PREFIX = "clientConfig.realm.";

    private AsyncHttpClient client;
    private AsyncHttpClientConfig clientConfig;
    private Binding binding;
    private SSLContextParameters sslContextParameters;
    private boolean allowJavaSerializedObject;

    public CallComponent() {
        super(CallEndpoint.class);
    }

    @Override
    protected Endpoint createEndpoint(String s, String s1, Map<String, Object> map) throws Exception {
        return null;
    }

    public AsyncHttpClient getClient() {
        return client;
    }

    /**
     * To use a custom {@link AsyncHttpClient}
     */
    public void setClient(AsyncHttpClient client) {
        this.client = client;
    }

    public Binding getBinding() {
        if (binding == null) {
            binding = new CallBinding();
        }
        return binding;
    }

    /**
     * To use a custom {@link Binding} which allows to control how to bind between AHC and Camel.
     */
    public void setBinding(Binding binding) {
        this.binding = binding;
    }

    public AsyncHttpClientConfig getClientConfig() {
        return clientConfig;
    }

    /**
     * To configure the AsyncHttpClient to use a custom com.ning.http.client.AsyncHttpClientConfig instance.
     */
    public void setClientConfig(AsyncHttpClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }

    public SSLContextParameters getSslContextParameters() {
        return sslContextParameters;
    }

    /**
     * Reference to a org.apache.camel.util.jsse.SSLContextParameters in the Registry.
     * Note that configuring this option will override any SSL/TLS configuration options provided through the
     * clientConfig option at the endpoint or component level.
     */
    public void setSslContextParameters(SSLContextParameters sslContextParameters) {
        this.sslContextParameters = sslContextParameters;
    }

    public boolean isAllowJavaSerializedObject() {
        return allowJavaSerializedObject;
    }

    /**
     * Whether to allow java serialization when a request uses context-type=application/x-java-serialized-object
     * <p/>
     * This is by default turned off. If you enable this then be aware that Java will deserialize the incoming
     * data from the request to Java and that can be a potential security risk.
     */
    public void setAllowJavaSerializedObject(boolean allowJavaSerializedObject) {
        this.allowJavaSerializedObject = allowJavaSerializedObject;
    }

    protected String createAddressUri(String uri, String remaining) {
        return remaining;
    }

    protected CallEndpoint createCallEndpoint(String endpointUri, CallComponent component, URI httpUri) {
        return new CallEndpoint(endpointUri, component, httpUri);
    }

    /**
     * Creates a new client configuration builder using {@code clientConfig} as a template for
     * the builder.
     *
     * @param clientConfig the instance to serve as a template for the builder
     * @return a builder configured with the same options as the supplied config
     */
    static AsyncHttpClientConfig.Builder cloneConfig(AsyncHttpClientConfig clientConfig) {
        AsyncHttpClientConfig.Builder builder = new AsyncHttpClientConfig.Builder(clientConfig);
        return builder;
    }
}
