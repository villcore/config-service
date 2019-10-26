package com.duduyixia.config.server.dto;

import com.duduyixia.config.server.bean.ConfigKey;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * created by WangTao on 2019-10-16
 */
public class ConfigWatcherDTO implements Serializable {

    private ConfigKey configKey;
    private List<ClientConfigInfo> clientConfigInfoList;

    public ConfigWatcherDTO() {
    }

    public ConfigWatcherDTO(ConfigKey configKey, List<ClientConfigInfo> clientConfigInfoList) {
        this.configKey = configKey;
        this.clientConfigInfoList = clientConfigInfoList;
    }

    public ConfigKey getConfigKey() {
        return configKey;
    }

    public void setConfigKey(ConfigKey configKey) {
        this.configKey = configKey;
    }

    public List<ClientConfigInfo> getClientConfigInfoList() {
        return clientConfigInfoList;
    }

    public void setClientConfigInfoList(List<ClientConfigInfo> clientConfigInfoList) {
        this.clientConfigInfoList = clientConfigInfoList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigWatcherDTO that = (ConfigWatcherDTO) o;
        return Objects.equals(configKey, that.configKey) &&
                Objects.equals(clientConfigInfoList, that.clientConfigInfoList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(configKey, clientConfigInfoList);
    }

    @Override
    public String toString() {
        return "ConfigWatcherDTO{" +
                "configKey=" + configKey +
                ", clientConfigInfoList=" + clientConfigInfoList +
                '}';
    }
}
