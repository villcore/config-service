package com.duduyixia.config.client.internal;

import com.duduyixia.config.client.*;
import com.duduyixia.config.client.internal.config.ConfigData;
import com.duduyixia.config.client.internal.config.ConfigKey;
import com.duduyixia.config.client.internal.executor.ConfigTaskExecutorGroup;
import com.duduyixia.config.client.internal.http.ConfigHttpClient;
import com.duduyixia.config.client.internal.wrapper.ConfigDataWrapperFactory;
import com.duduyixia.config.client.internal.wrapper.DefaultConfigDataWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.Executor;

/**
 * created by WangTao on 2019-09-16
 */
public class DefaultConfigService implements ConfigService {

    private static final Logger log = LoggerFactory.getLogger(DefaultConfigService.class);

    private final ConfigServiceEnv configServiceEnv;
    private final ConfigRegistry configRegistry;
    private final ConfigTaskExecutorGroup configTaskExecutorGroup;

    DefaultConfigService(ConfigServiceEnv configServiceEnv) {
        this.configServiceEnv = configServiceEnv;
        this.configRegistry = new ConfigRegistry();
        this.configTaskExecutorGroup = new ConfigTaskExecutorGroup();
    }

    @Override
    public String getConfig(String config) throws ConfigException {
        requireNonNull(config);
        DefaultConfigDataWrapper configDataWrapper = getDefaultConfigDataWrapper(config);
        return configDataWrapper.getConfigData().getValue();
    }

    private DefaultConfigDataWrapper getDefaultConfigDataWrapper(String config) {
        return getDefaultConfigDataWrapper(config, ConfigServiceEnv.DEFAULT_GROUP);
    }

    private DefaultConfigDataWrapper getDefaultConfigDataWrapper(String config, String group) {
        ConfigKey configKey = ConfigKey.valueOf(configServiceEnv, group, config);
        DefaultConfigDataWrapper configDataWrapper = configRegistry.getConfig(configKey);
        if (configDataWrapper == null) {
            configDataWrapper = createConfigDataWrapper(configServiceEnv, configKey);
        }
        return configDataWrapper;
    }

    private DefaultConfigDataWrapper createConfigDataWrapper(ConfigServiceEnv configServiceEnv, ConfigKey configKey) {
        DefaultConfigDataWrapper configDataWrapper;
        synchronized (configRegistry) {
            configDataWrapper = configRegistry.getConfig(configKey);
            if (configDataWrapper != null) {
                return configDataWrapper;
            }

            ConfigData configData = ConfigData.newConfigData(configKey);
            if (configServiceEnv.failover()) {
                // 本地文件 内存 容错的配置
                configDataWrapper = ConfigDataWrapperFactory.createFailoverConfigDataWrapper(
                        configServiceEnv, configData, configTaskExecutorGroup.next(), ConfigHttpClient.getInstance());
            } else {
                // 内存容错配置
                configDataWrapper = ConfigDataWrapperFactory.createConfigDataWrapper(configServiceEnv, configData,
                        configTaskExecutorGroup.next(), ConfigHttpClient.getInstance());

            }
            configRegistry.addConfig(configKey, configDataWrapper);
        }
        return configDataWrapper;
    }

    @Override
    public <T> T getConfig(String config, ConfigConverter<T> configConverter) throws ConfigException {
        requireNonNull(config, configConverter);
        String configValue = getConfig(config);
        return configConverter.convert(configValue);
    }

    @Override
    public String getConfig(String config, String defaultValue) throws ConfigException {
        requireNonNull(config);
        String configValue = getConfig(config);
        if (configValue == null) {
            return defaultValue;
        }
        return configValue;
    }

    @Override
    public <T> T getConfig(String config, T defaultValue, ConfigConverter<T> configConverter) throws ConfigException {
        requireNonNull(config, configConverter);
        String configValue = getConfig(config);
        if (configValue == null) {
            return defaultValue;
        }
        return configConverter.convert(configValue);
    }

    @Override
    public String getConfig(String config, String group, String defaultValue) throws ConfigException {
        requireNonNull(config, group);
        DefaultConfigDataWrapper configDataWrapper = getDefaultConfigDataWrapper(config, group);
        return configDataWrapper.getConfigData().getValue();
    }

    @Override
    public <T> T getConfig(String config, String group, T defaultValue, ConfigConverter<T> configConverter) throws ConfigException {
        requireNonNull(config, group, configConverter);
        String configValue = getConfig(config, group);
        if (configValue == null) {
            return defaultValue;
        }
        return configConverter.convert(configValue);
    }

    @Override
    public void addListener(String config, ConfigChangeListener listener) {
        requireNonNull(config, listener);
        getDefaultConfigDataWrapper(config).andListener(ListenerSupport.create(null, null, listener));
    }

    @Override
    public <T> void addListener(String config, GenericConfigChangeListener<T> listener, ConfigConverter<T> configConverter) {
        requireNonNull(config, listener, configConverter);
        getDefaultConfigDataWrapper(config).andListener(ListenerSupport.create(null, configConverter, listener));
    }

    @Override
    public void addListener(String config, String group, ConfigChangeListener listener) {
        requireNonNull(config, group, listener);
        getDefaultConfigDataWrapper(config, group).andListener(ListenerSupport.create(null, null, listener));
    }

    @Override
    public <T> void addListener(String config, String group, GenericConfigChangeListener<T> listener, ConfigConverter<T> configConverter) {
        requireNonNull(config, group, listener, configConverter);
        getDefaultConfigDataWrapper(config, group).andListener(ListenerSupport.create(null, configConverter, listener));
    }

    @Override
    public void addListener(String config, Executor executor, ConfigChangeListener listener) {
        requireNonNull(config, executor, listener);
        getDefaultConfigDataWrapper(config).andListener(ListenerSupport.create(executor, null, listener));
    }

    @Override
    public <T> void addListener(String config, Executor executor, GenericConfigChangeListener<T> listener, ConfigConverter<T> configConverter) {
        requireNonNull(config, executor, listener, configConverter);
        getDefaultConfigDataWrapper(config).andListener(ListenerSupport.create(executor, configConverter, listener));
    }

    @Override
    public void addListener(String config, String group, Executor executor, ConfigChangeListener listener) {
        requireNonNull(config, group, executor, listener);
        getDefaultConfigDataWrapper(config, group).andListener(ListenerSupport.create(executor, null, listener));
    }

    @Override
    public <T> void addListener(String config, String group, Executor executor, GenericConfigChangeListener<T> listener, ConfigConverter<T> configConverter) {
        requireNonNull(config, group, executor, listener, configConverter);
        getDefaultConfigDataWrapper(config, group).andListener(ListenerSupport.create(executor, configConverter, listener));
    }

    @Override
    public String getConfigAndSignListener(String config, String defaultValue, ConfigChangeListener listener) throws ConfigException {
        requireNonNull(config, listener, listener);
        return signListenerAndGetConfig(config, null, null, null, listener);
    }

    private <T> String signListenerAndGetConfig(String config, String group, Executor executor, ConfigConverter<T> configConverter, Listener listener) throws ConfigException {
        DefaultConfigDataWrapper configDataWrapper =
                group != null ? getDefaultConfigDataWrapper(config, group) : getDefaultConfigDataWrapper(config);
        configDataWrapper.andListener(ListenerSupport.create(executor, configConverter, listener));
        return configDataWrapper.getConfigData().getValue();
    }

    @Override
    public <T> T getConfigAndSignListener(String config, T defaultValue, GenericConfigChangeListener<T> listener, ConfigConverter<T> configConverter) throws ConfigException {
        requireNonNull(config, listener, configConverter);
        String configValue = signListenerAndGetConfig(config, null, null, configConverter, listener);
        if (configValue == null) {
            return defaultValue;
        }
        return configConverter.convert(configValue);
    }

    @Override
    public String getConfigAndSignListener(String config, String group, String defaultValue, ConfigChangeListener listener) throws ConfigException {
        requireNonNull(config, group, listener);
        return signListenerAndGetConfig(config, group, null, null, listener);
    }

    @Override
    public <T> T getConfigAndSignListener(String config, String group, T defaultValue, GenericConfigChangeListener<T> listener, ConfigConverter<T> configConverter) throws ConfigException {
        requireNonNull(config, group, listener, configConverter);
        String configValue = signListenerAndGetConfig(config, group, null, configConverter, listener);
        if (configValue == null) {
            return defaultValue;
        }
        return configConverter.convert(configValue);
    }

    @Override
    public String getConfigAndSignListener(String config, String defaultValue, Executor executor, ConfigChangeListener listener) throws ConfigException {
        requireNonNull(config, executor, listener);
        String configValue = signListenerAndGetConfig(config, null, executor, null, listener);
        if (configValue == null) {
            return defaultValue;
        }
        return configValue;
    }

    @Override
    public <T> T getConfigAndSignListener(String config, T defaultValue, Executor executor, GenericConfigChangeListener<T> listener, ConfigConverter<T> configConverter) throws ConfigException {
        requireNonNull(config, executor, listener, configConverter);
        String configValue = signListenerAndGetConfig(config, null, executor, configConverter, listener);
        if (configValue == null) {
            return defaultValue;
        }
        return configConverter.convert(configValue);
    }

    @Override
    public String getConfigAndSignListener(String config, String group, String defaultValue, Executor executor, ConfigChangeListener listener) throws ConfigException {
        requireNonNull(config, group, executor, listener);
        String configValue = signListenerAndGetConfig(config, group, null, null, listener);
        if (configValue == null) {
            return defaultValue;
        }
        return configValue;
    }

    @Override
    public <T> T getConfigAndSignListener(String config, String group, T defaultValue, Executor executor, GenericConfigChangeListener<T> listener, ConfigConverter<T> configConverter) throws ConfigException {
        requireNonNull(config, group, executor, listener, configConverter);
        String configValue = signListenerAndGetConfig(config, group, executor, configConverter, listener);
        if (configValue == null) {
            return defaultValue;
        }
        return configConverter.convert(configValue);
    }

    private void requireNonNull(Object... objects) {
        for (Object obj : objects) {
            Objects.requireNonNull(obj);
        }
    }
}
