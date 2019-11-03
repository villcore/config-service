package com.duduyixia.config.client;

import java.util.concurrent.Executor;

/**
 * created by WangTao on 2019-09-16
 */
public interface ConfigService {

    String getConfig(String config) throws ConfigException;

    <T> T getConfig(String config, ConfigConverter<T> configConverter) throws ConfigException;

    String getConfig(String config, String defaultValue) throws ConfigException;

    <T> T getConfig(String config, T defaultValue, ConfigConverter<T> configConverter) throws ConfigException;

    String getConfig(String config, String group, String defaultValue) throws ConfigException;

    <T> T getConfig(String config, String group, T defaultValue, ConfigConverter<T> configConverter) throws ConfigException;

    void addListener(String config, ConfigChangeListener listener);

    <T> void addListener(String config, GenericConfigChangeListener<T> listener, ConfigConverter<T> configConverter);

    void addListener(String config, String group, ConfigChangeListener listener);

    <T> void addListener(String config, String group, GenericConfigChangeListener<T> listener, ConfigConverter<T> configConverter);

    void addListener(String config, Executor executor, ConfigChangeListener listener);

    <T> void addListener(String config, Executor executor, GenericConfigChangeListener<T> listener, ConfigConverter<T> configConverter);

    void addListener(String config, String group, Executor executor, ConfigChangeListener listener);

    <T> void addListener(String config, String group, Executor executor, GenericConfigChangeListener<T> listener, ConfigConverter<T> configConverter);

    String getConfigAndSignListener(String config, String defaultValue, ConfigChangeListener listener) throws ConfigException;

    <T> T getConfigAndSignListener(String config, T defaultValue, GenericConfigChangeListener<T> listener, ConfigConverter<T> configConverter) throws ConfigException;

    String getConfigAndSignListener(String config, String group, String defaultValue, ConfigChangeListener listener) throws ConfigException;

    <T> T getConfigAndSignListener(String config, String group, T defaultValue, GenericConfigChangeListener<T> listener, ConfigConverter<T> configConverter) throws ConfigException;

    String getConfigAndSignListener(String config, String defaultValue, Executor executor, ConfigChangeListener listener) throws ConfigException;

    <T> T getConfigAndSignListener(String config, T defaultValue, Executor executor, GenericConfigChangeListener<T> listener, ConfigConverter<T> configConverter) throws ConfigException;

    String getConfigAndSignListener(String config, String group, String defaultValue, Executor executor, ConfigChangeListener listener) throws ConfigException;

    <T> T getConfigAndSignListener(String config, String group, T defaultValue, Executor executor, GenericConfigChangeListener<T> listener, ConfigConverter<T> configConverter) throws ConfigException;

}