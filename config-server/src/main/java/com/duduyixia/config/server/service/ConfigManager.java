package com.duduyixia.config.server.service;

import com.duduyixia.config.server.bean.ConfigBetaClient;
import com.duduyixia.config.server.bean.ConfigData;
import com.duduyixia.config.server.bean.ConfigKey;
import com.duduyixia.config.server.dao.rds.mapper.ConfigBetaClientMapper;
import com.duduyixia.config.server.dao.rds.mapper.ConfigDataMapper;
import com.duduyixia.config.server.event.EventSource;
import com.duduyixia.config.server.event.EventSources;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * created by WangTao on 2019-09-20
 */
@Component
public class ConfigManager {

    private static final Logger log = LoggerFactory.getLogger(ConfigManager.class);

    private static final ConfigData EMPTY_CONFIG = new ConfigData();

    // TODO: use config
    private final int maxConfigCacheSize = 2048;
    private final int maxConfigCachedSecond = 3600;
    private final int configCleanupIntervalSeconds = 60;
    private final int configCleanAllIntervalSeconds = 5 * 60;

    private final AtomicInteger configCounter;
    private final ScheduledExecutorService scheduler;
    private final LoadingCache<ConfigKey, ConfigData> configCache;
    private ScheduledFuture<?> cleanupSchedulerFuture ;
    private final EventSource<ConfigKey> configChangeEventSource;

    private final ConfigDataMapper configDataMapper;
    private final ConfigBetaClientMapper configBetaClientMapper;

    @Autowired
    public ConfigManager(ConfigDataMapper configDataMapper, ConfigBetaClientMapper configBetaClientMapper) {
        this.configDataMapper = configDataMapper;
        this.configBetaClientMapper = configBetaClientMapper;

        configCounter = new AtomicInteger(0);
        scheduler = Executors.newSingleThreadScheduledExecutor();
        configCache = createConfigCache();
        configChangeEventSource = EventSources.getConfigChangeEventSource();
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

        configChangeEventSource.subscribe(this::removeConfig);
    }

    private ConfigData loadConfig(ConfigKey configKey) {
        ConfigData configData = configDataMapper.getConfig(configKey);
        System.out.println(configData);
        if (configData == null) {
            return EMPTY_CONFIG;
        }

        if (configData.getBeta()) {
            List<ConfigBetaClient> configBetaClientList = configBetaClientMapper.getBetaIps(configData);
            configData.setConfigBetaClientList(configBetaClientList);
        }
        return configData;
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

    public ConfigData refresh(ConfigKey configKey) {
        ConfigData configData = loadConfig(configKey);
        configCache.put(configKey, configData);
        return configData;
    }

    public void removeConfig(ConfigKey configKey) {
        configCache.invalidate(configKey);
        cleanConfig();
    }

    public void clearAll() {
        configCache.invalidateAll();
        cleanConfig();
    }

    public static boolean isEmpty(ConfigData configData) {
        return EMPTY_CONFIG == configData;
    }
}
