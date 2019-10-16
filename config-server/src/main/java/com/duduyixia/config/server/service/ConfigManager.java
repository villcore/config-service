package com.duduyixia.config.server.service;

import com.duduyixia.config.server.bean.ConfigData;
import com.duduyixia.config.server.bean.ConfigKey;
import com.duduyixia.config.server.event.EventSource;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * created by WangTao on 2019-09-20
 */
public class ConfigManager {

    private static final Logger log = LoggerFactory.getLogger(ConfigManager.class);

    // TODO: use config
    private final int maxConfigCacheSize = 2048;
    private final int maxConfigCachedSecond = 3600;
    private final int configCleanupIntervalSeconds = 60;
    private final int configCleanAllIntervalSeconds = 5 * 60;

    private final AtomicInteger configCounter;
    private final ScheduledExecutorService scheduler;
    private final LoadingCache<ConfigKey, ConfigData> configCache;
    private ScheduledFuture<?> cleanupSchedulerFuture ;

    // TODO: listen event notify

    // TODO: maybe autowired
    public ConfigManager() {
        configCounter = new AtomicInteger(0);
        scheduler = Executors.newSingleThreadScheduledExecutor();
        configCache = createConfigCache();
        initialize();
    }

    private LoadingCache<ConfigKey, ConfigData> createConfigCache() {
        return CacheBuilder.newBuilder()
                .initialCapacity(maxConfigCacheSize)
                .maximumSize(maxConfigCacheSize)
                .expireAfterAccess(maxConfigCachedSecond, TimeUnit.SECONDS)
                .removalListener((RemovalListener<ConfigKey, ConfigData>) removalNotification -> {
                    configCounter.decrementAndGet();
                    log.info("Config [{}] removed", removalNotification.getKey());
                })
                .build(new CacheLoader<ConfigKey, ConfigData>() {
                    @Override
                    public ConfigData load(ConfigKey configKey) throws Exception {
                        configCounter.incrementAndGet();
                        return loadConfig(configKey);
                    }
                });
    }

    // TODO: maybe post constructor
    private void initialize() {
        if (cleanupSchedulerFuture != null) {
            return;
        }

        cleanupSchedulerFuture = scheduler.scheduleAtFixedRate(
                this::cleanConfig,
                configCleanupIntervalSeconds,
                configCleanupIntervalSeconds,
                TimeUnit.SECONDS);

        cleanupSchedulerFuture = scheduler.scheduleAtFixedRate(
                this::clearAll,
                configCleanAllIntervalSeconds,
                configCleanAllIntervalSeconds,
                TimeUnit.SECONDS);
    }

    private ConfigData loadConfig(ConfigKey configKey) {
        // TODO: ConfigDataMapper#getConfig
        // TODO: 如果不存在或已经删除
        ConfigData configData = new ConfigData();
        configData.setMarkDeleted(true);
    }

    private void cleanConfig() {
        configCache.cleanUp();
        log.info("Clean up config");
    }

    public ConfigData getConfig(ConfigKey configKey) {
        try {
            return configCache.get(configKey);
        } catch (ExecutionException e) {
            throw new RuntimeException("Get config " + configKey.toString() + " error ", e);
        }
    }

    public ConfigData updateConfig(ConfigKey configKey) {
        ConfigData configData = loadConfig(configKey);
        configCache.put(configKey, configData);
        return configData;
    }

    public void removeConfig(ConfigKey configKey) {
        configCache.invalidate(configKey);
    }

    public void clearAll() {
        configCache.invalidateAll();
        cleanConfig();
    }
}
