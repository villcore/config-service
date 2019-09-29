package com.duduyixia.config.client.internal.http;

import java.util.Map;

/**
 * created by WangTao on 2019-09-16
 */
public interface HttpClient {

    String doGet(String url, Map<String, String> header, Map<String, String> param, int timeoutMs) throws Exception;

    String doPost(String url, Map<String, String> header, Map<String, String> param, int timeoutMs) throws Exception;
}
