package com.villcore.config.client.internal;

import com.villcore.config.client.internal.config.ConfigKey;
import com.villcore.config.client.internal.wrapper.DefaultConfigDataWrapper;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * created by WangTao on 2019-09-19
 */
public class ConfigRegistry {

    private Map<ConfigKey, DefaultConfigDataWrapper> configDataWrapperMap = new ConcurrentHashMap<>();

    DefaultConfigDataWrapper getConfig(ConfigKey configKey) {
        Objects.requireNonNull(configKey, "configKey require not be null");
        return configDataWrapperMap.get(configKey);
    }

    void addConfig(ConfigKey configKey, DefaultConfigDataWrapper configDataWrapper) {
        Objects.requireNonNull(configKey, "configKey require not be null");
        Objects.requireNonNull(configDataWrapper, "configDataWrapper require not be null");
        configDataWrapperMap.put(configKey, configDataWrapper);
    }
}
