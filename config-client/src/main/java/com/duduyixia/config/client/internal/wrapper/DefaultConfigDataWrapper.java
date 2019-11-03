package com.duduyixia.config.client.internal.wrapper;

import com.duduyixia.config.client.*;
import com.duduyixia.config.client.internal.ConfigServiceEnv;
import com.duduyixia.config.client.internal.ListenerSupport;
import com.duduyixia.config.client.internal.config.ConfigData;
import com.duduyixia.config.client.internal.executor.ConfigTaskExecutor;
import com.duduyixia.config.client.internal.http.ConfigHttpClient;
import com.duduyixia.config.common.util.MD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;

/**
 * created by WangTao on 2019-09-21
 */
public class DefaultConfigDataWrapper {

    private static final Logger log = LoggerFactory.getLogger(DefaultConfigDataWrapper.class);

    private volatile ConfigData configData;
    private final ConfigServiceEnv configServiceEnv;
    private final ConfigHttpClient configHttpClient;
    private final CopyOnWriteArrayList<Listener> configChangeListeners;
    ConfigTaskExecutor configTaskExecutor;

    private final Object configDataLock = new Object();

    DefaultConfigDataWrapper(ConfigData configData, ConfigServiceEnv configServiceEnv, ConfigTaskExecutor configTaskExecutor,
                             ConfigHttpClient configHttpClient) {
        Objects.requireNonNull(configData, "configData require non null");
        this.configData = configData;
        this.configServiceEnv = configServiceEnv;
        this.configTaskExecutor = configTaskExecutor;
        this.configHttpClient = configHttpClient;
        this.configChangeListeners = new CopyOnWriteArrayList<>();
    }

    public ConfigData getConfigDataSnapshot() {
        return configData;
    }

    public ConfigData getConfigData() throws ConfigException {
        return getInternalConfigData();
    }

    private ConfigData getInternalConfigData() throws ConfigException {
        ConfigData configData = this.configData;
        if (configData.isMarkDeleted()) {
            log.warn("Config [{}] is marked deleted", configData.getConfigKey());
            return configData;
        }

        boolean initialized = false;
        if (configData.getValue() == null && configData.getUpdateTimeMs() == 0) {
            initialized = true;
        }

        if (initialized) {
            configData = refreshConfigData(configData);
            configData = tryUpdateConfig(configData);
        }

        if (configData.isMarkDeleted()) {
            log.warn("Config [{}] is marked deleted", configData.getConfigKey());
            return configData;
        }
        return configData;
    }

    public ConfigData tryUpdateConfig(ConfigData configData) {
        synchronized (configDataLock) {
            if (configData.getUpdateTimeMs() > this.configData.getUpdateTimeMs()) {
                this.configData = configData;
            } else {
                configData = this.configData;
            }
        }
        return configData;
    }

    public void refreshConfigUpdateTime() {
        ConfigData newConfigData = ConfigData.copyOf(configData);
        newConfigData.setUpdateTimeMs(System.currentTimeMillis());
        synchronized (configDataLock) {
            if (newConfigData.getUpdateTimeMs() > this.configData.getUpdateTimeMs()) {
                this.configData = newConfigData;
            }
        }
    }

    public ConfigData refreshConfigData() throws ConfigException {
        ConfigData configData = this.configData;
        return refreshConfigData(configData);
    }

    private ConfigData refreshConfigData(ConfigData configData) throws ConfigException {
        try {
            ConfigData serverConfigData = configHttpClient.getConfigOnce(configData.getConfigKey(), 500);
            boolean isMarkedDeleted = serverConfigData.isMarkDeleted();
            String configDataValue = serverConfigData.getValue();

            ConfigData newConfig = ConfigData.copyOf(configData);
            newConfig.setUpdateTimeMs(System.currentTimeMillis());
            newConfig.setMarkDeleted(isMarkedDeleted);
            newConfig.setValue(configDataValue);
            newConfig.setMd5(MD5.getInstance().getMD5String(configDataValue));
            return newConfig;
        } catch (Exception e) {
            throw new ConfigException(e);
        }
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - configData.getUpdateTimeMs() > configServiceEnv.configExpireTimeMs();
    }

    public void configMarkDeleted(ConfigData configData, ConfigData newConfigData) {
        try {
            log.warn("Config [{}] deleted", configData.getConfigKey());
        } catch (Exception e) {
            log.error("");
        }
    }

    public void configChanged(ConfigData configData, ConfigData newConfigData) {
        try {
            log.warn("Config [{}] changed, config [{}]=>[{}]", configData.getConfigKey(), configData.getValue(), newConfigData.getValue());
            notifyAllListener();
        } catch (Exception e) {
            log.error("");
        }
    }

    public void andListener(Listener listener) {
        configChangeListeners.add(listener);
    }

    public void removeListener(Listener listener) {
        configChangeListeners.remove(listener);
    }

    @SuppressWarnings("unchecked")
    private void notifyAllListener() {
        final ConfigData configData = getConfigDataSnapshot();
        for (Listener configChangeListener : configChangeListeners) {
            if (configChangeListener instanceof ListenerSupport) {
                ListenerSupport listenerSupport = (ListenerSupport) configChangeListener;
                Listener listener = listenerSupport.listener();

                if (listener instanceof ConfigChangeListener) {
                    try {
                        ((ConfigChangeListener) listener).onChange(configData.getValue());
                    } catch (Exception e) {
                        log.error("Config Listener onChange error", e);
                    }
                } else if (listener instanceof GenericConfigChangeListener) {
                    try {
                        GenericConfigChangeListener genericConfigChangeListener = (GenericConfigChangeListener) listener;
                        Executor executor = listenerSupport.executor();
                        ConfigConverter configConverter = listenerSupport.convert();
                        if (executor != null) {
                            Object obj = configConverter.convert(configData.getValue());
                            executor.execute(() -> genericConfigChangeListener.onChange(obj));
                        } else {
                            Object obj = configConverter.convert(configData.getValue());
                            genericConfigChangeListener.onChange(obj);
                        }
                    } catch (Exception e) {
                        log.error("Config Generic Listener onChange error", e);
                    }
                }
            }
        }
    }
}
