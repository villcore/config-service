package com.duduyixia.config.client.internal.http;

import com.duduyixia.config.client.internal.ConfigServiceEnv;
import com.duduyixia.config.client.internal.config.ConfigData;
import com.duduyixia.config.client.internal.config.ConfigKey;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    private final ObjectMapper objectMapper;

    private ConfigHttpClient() {
        configServiceEnv = ConfigServiceEnv.load();
        this.httpClient = new SimpleHttpClient(configServiceEnv);
        this.objectMapper = new ObjectMapper();
    }

    public ConfigData getConfigOnce(ConfigKey configKey, int timeoutMs) throws Exception {
        String url = configServiceEnv.getConfigServerUrl() + "/api/v1/config/get";
        String resp = this.httpClient.doGet(url, Collections.emptyMap(), Collections.singletonMap("config", ConfigKey.toFlatKey(configKey)), timeoutMs);
        return objectMapper.readValue(resp, ConfigData.class);
    }

    public ConfigData getConfig(ConfigKey configKey) throws Exception {
        int configListenIntervalMs = configServiceEnv.getConfigListenIntervalMs();
        return doRetryable(configListenIntervalMs, 5000,
                () -> getConfigOnce(configKey, 5000));
    }

    public List<ConfigKey> listenConfigChange(Map<ConfigKey, String> configMd5) throws Exception {
        String url = configServiceEnv.getConfigServerUrl() + "/api/v1/config/listen";
        final Map<String, String> flatConfigKey = new HashMap<>();
        configMd5.forEach((k, v) -> {
            flatConfigKey.put(ConfigKey.toFlatKey(k), v);
        });
        String configMd5Json = objectMapper.writeValueAsString(flatConfigKey);
        int configListenIntervalMs = configServiceEnv.getConfigListenIntervalMs();
        String resp = doRetryable(configListenIntervalMs, 5000,
                () -> this.httpClient.doPost(url, Collections.emptyMap(), Collections.singletonMap("config_md5", configMd5Json), configListenIntervalMs));
        List<String> flatConfigKeys = objectMapper.readValue(resp, new TypeReference<List<String>>() {
        });
        return flatConfigKeys.stream().map(ConfigKey::formFlatKey).collect(Collectors.toList());
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
