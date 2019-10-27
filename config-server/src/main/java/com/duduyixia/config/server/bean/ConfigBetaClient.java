package com.duduyixia.config.server.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class ConfigBetaClient implements Serializable {

    private Integer id;
    private Integer configId;
    private String ip;
    private boolean exist;
    private Date createTime;
    private Date updateTime;

    public ConfigBetaClient() {}

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigBetaClient that = (ConfigBetaClient) o;
        return exist == that.exist &&
                createTime == that.createTime &&
                updateTime == that.updateTime &&
                Objects.equals(id, that.id) &&
                Objects.equals(configId, that.configId) &&
                Objects.equals(ip, that.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, configId, ip, exist, createTime, updateTime);
    }

    @Override
    public String toString() {
        return "ConfigBetaClient{" +
                "id=" + id +
                ", configId=" + configId +
                ", ip='" + ip + '\'' +
                ", exist=" + exist +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
