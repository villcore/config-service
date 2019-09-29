package com.duduyixia.config.client.internal.http;

import com.duduyixia.config.client.internal.ConfigServiceEnv;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * created by WangTao on 2019-09-20
 */
public class SimpleHttpClient implements HttpClient {

    private final ConfigServiceEnv configServiceEnv;
    private final OkHttpClient okHttpClient;

    public SimpleHttpClient(ConfigServiceEnv configServiceEnv) {
        this.configServiceEnv = configServiceEnv;
        this.okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(configServiceEnv.getHttpConnectTimeoutMs(), TimeUnit.MILLISECONDS)
                .build();
    }

    @Override
    public String doGet(String url, Map<String, String> header, Map<String, String> param, int timeoutMs) throws Exception {
        Request.Builder builder = new Request.Builder();
        final StringBuilder targetUrl = new StringBuilder(url + "?");
        param = new HashMap<>(param);
        param.put("timeout", String.valueOf(timeoutMs));
        param.forEach((k, v) -> {
            targetUrl.append(k).append("=").append(v).append("&");
        });
        targetUrl.setLength(targetUrl.length() - 1);
        builder.url(targetUrl.toString());
        header.forEach(builder::addHeader);
        builder.get();
        byte[] bytes = getSpecificReadTimeoutClient(timeoutMs).newCall(builder.build()).execute().body().bytes();
        return new String(bytes, ConfigServiceEnv.CHARSET);
    }

    @Override
    public String doPost(String url, Map<String, String> header, Map<String, String> param, int timeoutMs) throws Exception  {
        Request.Builder builder = new Request.Builder();
        header.forEach(builder::addHeader);
        FormBody.Builder formBuilder = new FormBody.Builder();
        param = new HashMap<>(param);
        param.put("timeout", String.valueOf(timeoutMs));
        param.forEach(formBuilder::add);
        builder.post(formBuilder.build());
        builder.url(url);
        byte[] bytes = getSpecificReadTimeoutClient(timeoutMs).newCall(builder.build()).execute().body().bytes();
        return new String(bytes, ConfigServiceEnv.CHARSET);
    }

    private OkHttpClient getSpecificReadTimeoutClient(int readTimeoutMs) {
        return this.okHttpClient.newBuilder()
                .connectTimeout(readTimeoutMs, TimeUnit.MILLISECONDS)
                .readTimeout(readTimeoutMs, TimeUnit.MILLISECONDS)
                .build();
    }
}
