package com.duduyixia.config.client.internal;

import com.duduyixia.config.client.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Objects;
import java.util.Properties;

/**
 * created by WangTao on 2019-09-21
 */
public final class ConfigServiceEnv {

    private static final Logger log = LoggerFactory.getLogger(ConfigServiceEnv.class);

    public static final String CHARSET = "utf-8";

    private static final String NAMESPACE_KEY =                                 "config.client.namespace";
    private static final String ENV_KEY =                                       "config.client.env";
    private static final String APP_KEY =                                       "config.client.app";
    private static final String FAILOVER_KEY =                                  "config.client.failover";
    private static final String CONFIG_EXPIRED_MS_KEY =                         "config.client.config.expired.ms";
    private static final String CONFIG_FILE_DIR_KEY =                           "config.client.file.dir";
    private static final String CONFIG_SERVER_CONNECT_TIMEOUT_MS_KEY =          "config.client.http.connect.timeout.ms";
    private static final String CONFIG_SERVER_LISTEN_TIMEOUT_MS_KEY =           "config.client.config.listen.timeout.ms";
    private static final String CONFIG_SERVER_URL_KEY =                         "config.client.server.url";
    private static final String CONFIG_SERVER_RETRY_INTERVAL_MS_KEY =           "config.client.server.retry.interval.ms";

    public static final String DEFAULT_NAMESPACE =                              "default";
    public static final String DEFAULT_ENV =                                    "prod";
    public static final String DEFAULT_GROUP =                                  "default_group";
    public static final String DEFAULT_APP =                                    "default_app";
    public static final String DEFAULT_FAILOVER =                               "false";
    public static final String DEFAULT_CONFIG_EXPIRED_MS =                      String.valueOf(5 * 60 * 1000L);
    public static final String DEFAULT_HTTP_CONNECT_TIMEOUT_MS =                String.valueOf(5 * 1000L);
    public static final String DEFAULT_CONFIG_LISTEN_INTERVAL_MS =              String.valueOf(30 * 1000L);
    public static final String DEFAULT_CONFIG_SERVER_URL =                      "http://localhost:8080";
    public static final long CONFIG_SAVED_INTERVAL_MS =                         1 * 60 * 1000L;
    public static final String DEFAULT_CONFIG_SERVER_RETRY_INTERVAL_MS =        String.valueOf(5 * 1000L);

    private final String namespace;
    private final String env;
    private final String app;
    private final boolean failover;
    private final long configExpiredTimeMs;
    private final int httpConnectTimeoutMs;
    private final int configListenIntervalMs;
    private final String configServerUrl;
    private final String configFileDir;
    private final int configServerRetryIntervalMs;

    private static ConfigServiceEnv INSTANCE;

    public static ConfigServiceEnv load() {
        if (INSTANCE != null) {
            return INSTANCE;
        }

        synchronized (ConfigServiceEnv.class) {
            Properties properties = System.getProperties();
            INSTANCE = new ConfigServiceEnv(properties);
        }
        return INSTANCE;
    }

    private ConfigServiceEnv(Properties properties) {
        if (properties == null || properties.isEmpty()) {
            log.warn("Config service env load failed, use default env");
            properties = new Properties();
        }

        namespace = properties.getProperty(NAMESPACE_KEY, DEFAULT_NAMESPACE);
        env = properties.getProperty(ENV_KEY, DEFAULT_ENV);
        app = properties.getProperty(APP_KEY, DEFAULT_APP);
        failover = Objects.equals(properties.getProperty(FAILOVER_KEY, DEFAULT_FAILOVER), "true");
        configExpiredTimeMs = Long.valueOf(properties.getProperty(CONFIG_EXPIRED_MS_KEY, DEFAULT_CONFIG_EXPIRED_MS));
        configFileDir = properties.getProperty(CONFIG_FILE_DIR_KEY, (System.getProperty("user.home")) + File.separator + "config-root" + File.separator);
        httpConnectTimeoutMs = Integer.valueOf(properties.getProperty(CONFIG_SERVER_CONNECT_TIMEOUT_MS_KEY, DEFAULT_HTTP_CONNECT_TIMEOUT_MS));
        configListenIntervalMs = Integer.valueOf(properties.getProperty(CONFIG_SERVER_LISTEN_TIMEOUT_MS_KEY, DEFAULT_CONFIG_LISTEN_INTERVAL_MS));
        configServerUrl = properties.getProperty(CONFIG_SERVER_URL_KEY, DEFAULT_CONFIG_SERVER_URL);
        configServerRetryIntervalMs = Integer.valueOf(properties.getProperty(CONFIG_SERVER_RETRY_INTERVAL_MS_KEY, DEFAULT_CONFIG_SERVER_RETRY_INTERVAL_MS));
        printEnvInfo();
    }

    private void printEnvInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n")
                .append("\t").append(NAMESPACE_KEY).append(" = ").append(namespace).append("\n")
                .append("\t").append(ENV_KEY).append(" = ").append(env).append("\n")
                .append("\t").append(APP_KEY).append(" = ").append(app).append("\n")
                .append("\t").append(FAILOVER_KEY).append(" = ").append(failover).append("\n")
                .append("\t").append(CONFIG_EXPIRED_MS_KEY).append(" = ").append(configExpiredTimeMs).append("\n")
                .append("\t").append(CONFIG_FILE_DIR_KEY).append(" = ").append(configFileDir).append("\n")
                .append("\t").append(CONFIG_SERVER_CONNECT_TIMEOUT_MS_KEY).append(" = ").append(httpConnectTimeoutMs).append("\n")
                .append("\t").append(CONFIG_SERVER_LISTEN_TIMEOUT_MS_KEY).append(" = ").append(configListenIntervalMs).append("\n")
                .append("\t").append(CONFIG_SERVER_URL_KEY).append(" = ").append(configServerUrl).append("\n")
                .append("\t").append(CONFIG_SERVER_RETRY_INTERVAL_MS_KEY).append(" = ").append(configServerRetryIntervalMs).append("\n");
        sb.append("\n");
        log.info("ConfigClient env : {}", sb.toString());
    }

    public String getNamespace() {
        return namespace;
    }

    public String getEnv() {
        return env;
    }

    public String getApp() {
        return app;
    }

    public long configExpireTimeMs() {
        if (configExpiredTimeMs <= 0) {
            log.warn("Config value [config.client.config.expired.ms] incorrect, use default config value [{}]", DEFAULT_CONFIG_EXPIRED_MS);
            return Long.valueOf(DEFAULT_CONFIG_EXPIRED_MS);
        }
        return configExpiredTimeMs;
    }

    public boolean failover() {
        return failover;
    }

    public String getConfigFileDir() {
        return configFileDir;
    }

    public int getHttpConnectTimeoutMs() {
        return httpConnectTimeoutMs;
    }

    public int getConfigListenIntervalMs() {
        return configListenIntervalMs;
    }

    public String getConfigServerUrl() {
        return configServerUrl;
    }

    public int getConfigServerRetryIntervalMs() {
        return configServerRetryIntervalMs;
    }
}
