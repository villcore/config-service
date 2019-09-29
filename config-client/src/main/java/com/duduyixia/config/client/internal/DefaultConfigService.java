package com.duduyixia.config.client.internal;

import com.duduyixia.config.client.ConfigChangeListener;
import com.duduyixia.config.client.ConfigException;
import com.duduyixia.config.client.ConfigService;
import com.duduyixia.config.client.internal.config.ConfigData;
import com.duduyixia.config.client.internal.config.ConfigKey;
import com.duduyixia.config.client.internal.executor.ConfigTaskExecutorGroup;
import com.duduyixia.config.client.internal.http.ConfigHttpClient;
import com.duduyixia.config.client.internal.wrapper.ConfigDataWrapperFactory;
import com.duduyixia.config.client.internal.wrapper.DefaultConfigDataWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        DefaultConfigDataWrapper configDataWrapper = getDefaultConfigDataWrapper(config);
        return configDataWrapper.getConfigData().getValue();
    }

    private DefaultConfigDataWrapper getDefaultConfigDataWrapper(String config) {
        ConfigKey configKey = ConfigKey.valueOf(configServiceEnv, ConfigServiceEnv.DEFAULT_GROUP, config);
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

//    @Override
//    public <T> T getConfig(String config, com.duduyixia.config.client.ConfigConverter<T> configConverter) throws com.duduyixia.config.client.ConfigException {
//        String configValue = getConfig(config);
//        return configConverter.convert(configValue);
//    }
//
//    @Override
//    public String getConfig(String config, String defaultValue) throws com.duduyixia.config.client.ConfigException {
//        String configValue = getConfig(config);
//        if (configValue == null) {
//            return defaultValue;
//        }
//        return configValue;
//    }
//
//    @Override
//    public <T> T getConfig(String config, T defaultValue, com.duduyixia.config.client.ConfigConverter<T> configConverter) throws com.duduyixia.config.client.ConfigException {
//        String configValue = getConfig(config);
//        if (configValue == null) {
//            return defaultValue;
//        }
//        return configConverter.convert(configValue);
//    }
//
//    @Override
//    public String getConfig(String config, String group, String defaultValue) throws com.duduyixia.config.client.ConfigException {
//        return null;
//    }
//
//    @Override
//    public <T> T getConfig(String config, String group, T defaultValue, com.duduyixia.config.client.ConfigConverter<T> configConverter) throws com.duduyixia.config.client.ConfigException {
//        return null;
//    }
//
    @Override
    public void addListener(String config, ConfigChangeListener listener) throws ConfigException {
        getDefaultConfigDataWrapper(config).andListener(listener);
    }
//
//    @Override
//    public <T> void addListener(String config, com.duduyixia.config.client.GenericConfigChangeListener<T> listener, com.duduyixia.config.client.ConfigConverter<T> configConverter) throws com.duduyixia.config.client.ConfigException {
//
//    }
//
//    @Override
//    public void addListener(String config, String group, com.duduyixia.config.client.ConfigChangeListener listener) throws com.duduyixia.config.client.ConfigException {
//
//    }
//
//    @Override
//    public <T> void addListener(String config, String group, com.duduyixia.config.client.GenericConfigChangeListener<T> listener, com.duduyixia.config.client.ConfigConverter<T> configConverter) throws com.duduyixia.config.client.ConfigException {
//
//    }
//
//    @Override
//    public void addListener(String config, Executor executor, com.duduyixia.config.client.ConfigChangeListener listener) throws com.duduyixia.config.client.ConfigException {
//
//    }
//
//    @Override
//    public <T> void addListener(String config, Executor executor, com.duduyixia.config.client.GenericConfigChangeListener<T> listener, com.duduyixia.config.client.ConfigConverter<T> configConverter) throws com.duduyixia.config.client.ConfigException {
//
//    }
//
//    @Override
//    public void addListener(String config, String group, Executor executor, com.duduyixia.config.client.ConfigChangeListener listener) throws com.duduyixia.config.client.ConfigException {
//
//    }
//
//    @Override
//    public <T> void addListener(String config, String group, Executor executor, com.duduyixia.config.client.GenericConfigChangeListener<T> listener, com.duduyixia.config.client.ConfigConverter<T> configConverter) throws com.duduyixia.config.client.ConfigException {
//
//    }
//
//    @Override
//    public String getConfigAndSignListener(String config, String defaultValue, com.duduyixia.config.client.ConfigChangeListener listener) throws com.duduyixia.config.client.ConfigException {
//        return null;
//    }
//
//    @Override
//    public <T> T getConfigAndSignListener(String config, T defaultValue, com.duduyixia.config.client.GenericConfigChangeListener<T> listener, com.duduyixia.config.client.ConfigConverter<T> configConverter) throws com.duduyixia.config.client.ConfigException {
//        return null;
//    }
//
//    @Override
//    public String getConfigAndSignListener(String config, String group, String defaultValue, com.duduyixia.config.client.ConfigChangeListener listener) throws com.duduyixia.config.client.ConfigException {
//        return null;
//    }
//
//    @Override
//    public <T> T getConfigAndSignListener(String config, String group, T defaultValue, com.duduyixia.config.client.GenericConfigChangeListener<T> listener, com.duduyixia.config.client.ConfigConverter<T> configConverter) throws com.duduyixia.config.client.ConfigException {
//        return null;
//    }
//
//    @Override
//    public String getConfigAndSignListener(String config, String defaultValue, Executor executor, com.duduyixia.config.client.ConfigChangeListener listener) throws com.duduyixia.config.client.ConfigException {
//        return null;
//    }
//
//    @Override
//    public <T> T getConfigAndSignListener(String config, T defaultValue, Executor executor, com.duduyixia.config.client.GenericConfigChangeListener<T> listener, com.duduyixia.config.client.ConfigConverter<T> configConverter) throws com.duduyixia.config.client.ConfigException {
//        return null;
//    }
//
//    @Override
//    public String getConfigAndSignListener(String config, String group, String defaultValue, Executor executor, com.duduyixia.config.client.ConfigChangeListener listener) throws com.duduyixia.config.client.ConfigException {
//        return null;
//    }
//
//    @Override
//    public <T> T getConfigAndSignListener(String config, String group, T defaultValue, Executor executor, com.duduyixia.config.client.GenericConfigChangeListener<T> listener, com.duduyixia.config.client.ConfigConverter<T> configConverter) throws com.duduyixia.config.client.ConfigException {
//        return null;
//    }
//
//    @Override
//    public void removeListener(String config, com.duduyixia.config.client.ConfigChangeListener listener) throws com.duduyixia.config.client.ConfigException {
//
//    }
//
//    @Override
//    public <T> void removeListener(String config, com.duduyixia.config.client.GenericConfigChangeListener<T> listener) throws com.duduyixia.config.client.ConfigException {
//
//    }
//
//    @Override
//    public void removeListener(String config, String group, com.duduyixia.config.client.ConfigChangeListener listener) throws com.duduyixia.config.client.ConfigException {
//
//    }
//
//    @Override
//    public <T> void removeListener(String config, String group, com.duduyixia.config.client.GenericConfigChangeListener<T> listener) throws com.duduyixia.config.client.ConfigException {
//
//    }
}
