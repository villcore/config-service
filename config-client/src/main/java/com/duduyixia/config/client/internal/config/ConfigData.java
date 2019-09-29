package com.duduyixia.config.client.internal.config;

import java.io.Serializable;
import java.util.Objects;

/**
 * created by WangTao on 2019-09-19
 */
public class ConfigData implements Serializable {

    private String namespace;
    private String env;
    private String app;
    private String group;
    private String config;
    private String value;
    private String md5;
    private boolean markDeleted;
    private long updateTimeMs;

    private ConfigKey configKey;

    private ConfigData() {}

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getUpdateTimeMs() {
        return updateTimeMs;
    }

    public void setUpdateTimeMs(long updateTimeMs) {
        this.updateTimeMs = updateTimeMs;
    }

    public boolean isMarkDeleted() {
        return markDeleted;
    }

    public void setMarkDeleted(boolean markDeleted) {
        this.markDeleted = markDeleted;
    }

    public ConfigKey getConfigKey() {
        return configKey;
    }

    public void setConfigKey(ConfigKey configKey) {
        this.configKey = configKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigData that = (ConfigData) o;
        return updateTimeMs == that.updateTimeMs &&
                markDeleted == that.markDeleted &&
                Objects.equals(namespace, that.namespace) &&
                Objects.equals(env, that.env) &&
                Objects.equals(app, that.app) &&
                Objects.equals(group, that.group) &&
                Objects.equals(config, that.config) &&
                Objects.equals(value, that.value) &&
                Objects.equals(md5, that.md5) &&
                Objects.equals(configKey, that.configKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, env, app, group, config, value, md5, updateTimeMs, markDeleted, configKey);
    }

    @Override
    public String toString() {
        return "ConfigData{" +
                "namespace='" + namespace + '\'' +
                ", env='" + env + '\'' +
                ", app='" + app + '\'' +
                ", group='" + group + '\'' +
                ", config='" + config + '\'' +
                ", value='" + value + '\'' +
                ", md5='" + md5 + '\'' +
                ", updateTimeMs=" + updateTimeMs +
                ", markDeleted=" + markDeleted +
                ", configKey=" + configKey +
                '}';
    }

    public static ConfigData copyOf(ConfigData configData) {
        Objects.requireNonNull(configData);

        ConfigData newConfig = new ConfigData();
        newConfig.setNamespace(configData.getNamespace());
        newConfig.setEnv(configData.getEnv());
        newConfig.setApp(configData.getApp());
        newConfig.setGroup(configData.getGroup());
        newConfig.setConfig(configData.getConfig());
        newConfig.setValue(configData.getValue());
        newConfig.setMd5(configData.getMd5());
        newConfig.setUpdateTimeMs(configData.getUpdateTimeMs());
        newConfig.setMarkDeleted(configData.isMarkDeleted());
        newConfig.setConfigKey(configData.getConfigKey());
        return newConfig;
    }

    public static ConfigData newConfigData(ConfigKey configKey) {
        ConfigData newConfig = new ConfigData();
        newConfig.setNamespace(configKey.getNamespace());
        newConfig.setEnv(configKey.getEnv());
        newConfig.setApp(configKey.getApp());
        newConfig.setGroup(configKey.getGroup());
        newConfig.setConfig(configKey.getConfig());
        newConfig.setValue(null);
        newConfig.setMd5(null);
        newConfig.setUpdateTimeMs(0);
        newConfig.setMarkDeleted(false);
        newConfig.setConfigKey(configKey);
        return newConfig;
    }
}
