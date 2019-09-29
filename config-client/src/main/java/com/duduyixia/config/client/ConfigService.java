package com.duduyixia.config.client;

/**
 * created by WangTao on 2019-09-16
 */
public interface ConfigService {

    String getConfig(String config) throws ConfigException;

//    <T> T getConfig(String config, com.duduyixia.config.client.ConfigConverter<T> configConverter) throws com.duduyixia.config.client.ConfigException;
//
//    String getConfig(String config, String defaultValue) throws com.duduyixia.config.client.ConfigException;
//
//    <T> T getConfig(String config, T defaultValue, com.duduyixia.config.client.ConfigConverter<T> configConverter) throws com.duduyixia.config.client.ConfigException;
//
//    String getConfig(String config, String group, String defaultValue) throws com.duduyixia.config.client.ConfigException;
//
//    <T> T getConfig(String config, String group, T defaultValue, com.duduyixia.config.client.ConfigConverter<T> configConverter) throws com.duduyixia.config.client.ConfigException;
//
    void addListener(String config, ConfigChangeListener listener) throws ConfigException;
//
//    <T> void addListener(String config, com.duduyixia.config.client.GenericConfigChangeListener<T> listener, com.duduyixia.config.client.ConfigConverter<T> configConverter) throws com.duduyixia.config.client.ConfigException;
//
//    void addListener(String config, String group, com.duduyixia.config.client.ConfigChangeListener listener) throws com.duduyixia.config.client.ConfigException;
//
//    <T> void addListener(String config, String group, com.duduyixia.config.client.GenericConfigChangeListener<T> listener, com.duduyixia.config.client.ConfigConverter<T> configConverter) throws com.duduyixia.config.client.ConfigException;
//
//    void addListener(String config, Executor executor, com.duduyixia.config.client.ConfigChangeListener listener) throws com.duduyixia.config.client.ConfigException;
//
//    <T> void addListener(String config, Executor executor, com.duduyixia.config.client.GenericConfigChangeListener<T> listener, com.duduyixia.config.client.ConfigConverter<T> configConverter) throws com.duduyixia.config.client.ConfigException;
//
//    void addListener(String config, String group, Executor executor, com.duduyixia.config.client.ConfigChangeListener listener) throws com.duduyixia.config.client.ConfigException;
//
//    <T> void addListener(String config, String group, Executor executor, com.duduyixia.config.client.GenericConfigChangeListener<T> listener, com.duduyixia.config.client.ConfigConverter<T> configConverter) throws com.duduyixia.config.client.ConfigException;
//
//    String getConfigAndSignListener(String config, String defaultValue, com.duduyixia.config.client.ConfigChangeListener listener) throws com.duduyixia.config.client.ConfigException;
//
//    <T> T getConfigAndSignListener(String config, T defaultValue, com.duduyixia.config.client.GenericConfigChangeListener<T> listener, com.duduyixia.config.client.ConfigConverter<T> configConverter) throws com.duduyixia.config.client.ConfigException;
//
//    String getConfigAndSignListener(String config, String group, String defaultValue, com.duduyixia.config.client.ConfigChangeListener listener) throws com.duduyixia.config.client.ConfigException;
//
//    <T> T getConfigAndSignListener(String config, String group, T defaultValue, com.duduyixia.config.client.GenericConfigChangeListener<T> listener, com.duduyixia.config.client.ConfigConverter<T> configConverter) throws com.duduyixia.config.client.ConfigException;
//
//    String getConfigAndSignListener(String config, String defaultValue, Executor executor, com.duduyixia.config.client.ConfigChangeListener listener) throws com.duduyixia.config.client.ConfigException;
//
//    <T> T getConfigAndSignListener(String config, T defaultValue, Executor executor, com.duduyixia.config.client.GenericConfigChangeListener<T> listener, com.duduyixia.config.client.ConfigConverter<T> configConverter) throws com.duduyixia.config.client.ConfigException;
//
//    String getConfigAndSignListener(String config, String group, String defaultValue, Executor executor, com.duduyixia.config.client.ConfigChangeListener listener) throws com.duduyixia.config.client.ConfigException;
//
//    <T> T getConfigAndSignListener(String config, String group, T defaultValue, Executor executor, com.duduyixia.config.client.GenericConfigChangeListener<T> listener, com.duduyixia.config.client.ConfigConverter<T> configConverter) throws com.duduyixia.config.client.ConfigException;
//
//    void removeListener(String config, com.duduyixia.config.client.ConfigChangeListener listener) throws com.duduyixia.config.client.ConfigException;
//
//    <T> void removeListener(String config, com.duduyixia.config.client.GenericConfigChangeListener<T> listener) throws com.duduyixia.config.client.ConfigException;
//
//    void removeListener(String config, String group, com.duduyixia.config.client.ConfigChangeListener listener) throws com.duduyixia.config.client.ConfigException;
//
//    <T> void removeListener(String config, String group, com.duduyixia.config.client.GenericConfigChangeListener<T> listener) throws com.duduyixia.config.client.ConfigException;

}