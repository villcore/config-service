package com.duduyixia.config.client.internal.http;

import com.duduyixia.config.client.ConfigException;
import com.duduyixia.config.client.internal.ConfigServiceEnv;
import com.duduyixia.config.client.internal.config.ConfigData;
import com.duduyixia.config.client.internal.config.ConfigKey;
import com.duduyixia.config.client.internal.config.WatchConfig;
import com.duduyixia.config.client.internal.util.JsonUtil;
import com.duduyixia.config.common.http.Response;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * created by WangTao on 2019-09-22
 */
public class ConfigHttpClient {

    private static final Logger log = LoggerFactory.getLogger(ConfigHttpClient.class);

    private static ConfigHttpClient instance;

    public static ConfigHttpClient getInstance() {
        if (instance == null) {
            synchronized (ConfigHttpClient.class) {
                if (instance == null) {
                    instance = new ConfigHttpClient();
                }
            }
        }
        return instance;
    }

    private final ConfigServiceEnv configServiceEnv;
    private final HttpClient httpClient;

    private ConfigHttpClient() {
        configServiceEnv = ConfigServiceEnv.load();
        this.httpClient = new SimpleHttpClient(configServiceEnv);
    }

    public ConfigData getConfigOnce(ConfigKey configKey, int timeoutMs) throws Exception {
        String url = configServiceEnv.getConfigServerUrl() + "/api/v1/config/get";
        String resp = this.httpClient.doGet(url, Collections.emptyMap(),
                Collections.singletonMap("configKey", ConfigKey.toFlatKey(configKey)), timeoutMs);
        System.out.println(resp);
        Response<ConfigData> response = JsonUtil.fromJson(resp, new TypeReference<Response<ConfigData>>(){});
        ConfigData configData;
        if (response == null || response.getCode() != 0 || (configData = response.getData()) == null) {
            throw new ConfigException(String.format("Config response %s error for config key %s", resp, ConfigKey.toFlatKey(configKey)));
        }
        return configData;
    }

    public ConfigData getConfig(ConfigKey configKey) throws Exception {
        int configListenIntervalMs = configServiceEnv.getConfigListenIntervalMs();
        return doRetryable(configListenIntervalMs, 5000,
                () -> getConfigOnce(configKey, 5000));
    }

    public List<ConfigKey> listenConfigChange(Map<ConfigKey, String> configMd5) throws Exception {
        if (configMd5.isEmpty()) {
            return Collections.emptyList();
        }

        String url = configServiceEnv.getConfigServerUrl() + "/api/v1/config/watch";
        List<WatchConfig> watchConfigs = new ArrayList<>(configMd5.size());
        configMd5.forEach((k, v) -> {
            watchConfigs.add(new WatchConfig(ConfigKey.toFlatKey(k), v));
        });
        String configMd5Json = JsonUtil.toJson(watchConfigs);
        int configListenIntervalMs = configServiceEnv.getConfigListenIntervalMs();
        String resp = doRetryable(configListenIntervalMs, 5000,
                () -> this.httpClient.doPost(url,
                        //Collections.singletonMap("content-type", "application/json;charset=UTF-8"),
                        Collections.emptyMap(),
                        Collections.singletonMap("configMd5", configMd5Json),
                        configListenIntervalMs));
        System.out.println("===" + resp);
        Response<List<ConfigKey>> response = JsonUtil.fromJson(resp, new TypeReference<Response<List<ConfigKey>>>(){});
        List<ConfigKey> changedConfigKey;
        if (response == null || response.getCode() != 0 || (changedConfigKey = response.getData()) == null) {
            throw new ConfigException(String.format("Config response %s error for config md5 %s", resp, configMd5Json));
        }
        return changedConfigKey;
    }

    private <T> T doRetryable(int timeoutMs, int retryIntervalTimeMs, Callable<T> get) throws Exception {
        long startTimeMillis = System.currentTimeMillis();
        long deadlineTimeMillis = startTimeMillis + timeoutMs;

        Exception throwable;
        do {
            try {
                return get.call();
            } catch (Exception e) {
                throwable = e;
                log.warn("Retry do http operation error: {}", e.getMessage());
                safeSleep(retryIntervalTimeMs);
            }
        } while (System.currentTimeMillis() < deadlineTimeMillis);
        throw throwable;
    }

    private void safeSleep(int sleepTimeMs) {
        try {
            TimeUnit.MILLISECONDS.sleep(sleepTimeMs);
        } catch (InterruptedException ignore) {

        }
    }
}
