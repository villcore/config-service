package com.duduyixia.config.server.bean;

import java.io.Serializable;
import java.util.Objects;

public class ConfigBetaIp implements Serializable {

    private Integer id;
    private Integer configId;
    private String ip;
    private boolean exist;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigBetaIp that = (ConfigBetaIp) o;
        return exist == that.exist &&
                Objects.equals(id, that.id) &&
                Objects.equals(configId, that.configId) &&
                Objects.equals(ip, that.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, configId, ip, exist);
    }

    @Override
    public String toString() {
        return "ConfigBetaIp{" +
                "id=" + id +
                ", configId=" + configId +
                ", ip='" + ip + '\'' +
                ", exist=" + exist +
                '}';
    }
}
