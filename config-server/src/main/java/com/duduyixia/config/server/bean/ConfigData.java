package com.duduyixia.config.server.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * created by WangTao on 2019-09-19
 */
public class ConfigData implements Serializable {

    private Integer id;
    private String namespace;
    private String env;
    private String app;
    private String group;
    private String config;
    private String value;
    private String md5;
    private boolean markDeleted;
    private boolean beta;
    private String betaValue;
    private String betaMd5;
    private Date createTime;
    private Date updateTime;

    // not db field
    private List<ConfigBetaClient> configBetaClientList;

    public ConfigData() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public boolean isMarkDeleted() {
        return markDeleted;
    }

    public void setMarkDeleted(boolean markDeleted) {
        this.markDeleted = markDeleted;
    }

    public boolean isBeta() {
        return beta;
    }

    public void setBeta(boolean beta) {
        this.beta = beta;
    }

    public String getBetaValue() {
        return betaValue;
    }

    public void setBetaValue(String betaValue) {
        this.betaValue = betaValue;
    }

    public String getBetaMd5() {
        return betaMd5;
    }

    public void setBetaMd5(String betaMd5) {
        this.betaMd5 = betaMd5;
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

    public List<ConfigBetaClient> getConfigBetaClientList() {
        return configBetaClientList;
    }

    public void setConfigBetaClientList(List<ConfigBetaClient> configBetaClientList) {
        this.configBetaClientList = configBetaClientList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigData that = (ConfigData) o;
        return markDeleted == that.markDeleted &&
                beta == that.beta &&
                createTime == that.createTime &&
                updateTime == that.updateTime &&
                Objects.equals(id, that.id) &&
                Objects.equals(namespace, that.namespace) &&
                Objects.equals(env, that.env) &&
                Objects.equals(app, that.app) &&
                Objects.equals(group, that.group) &&
                Objects.equals(config, that.config) &&
                Objects.equals(value, that.value) &&
                Objects.equals(md5, that.md5) &&
                Objects.equals(betaValue, that.betaValue) &&
                Objects.equals(betaMd5, that.betaMd5) &&
                Objects.equals(configBetaClientList, that.configBetaClientList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, namespace, env, app, group, config, value, md5, markDeleted, beta, betaValue, betaMd5, createTime, updateTime, configBetaClientList);
    }

    @Override
    public String toString() {
        return "ConfigData{" +
                "id=" + id +
                ", namespace='" + namespace + '\'' +
                ", env='" + env + '\'' +
                ", app='" + app + '\'' +
                ", group='" + group + '\'' +
                ", config='" + config + '\'' +
                ", value='" + value + '\'' +
                ", md5='" + md5 + '\'' +
                ", markDeleted=" + markDeleted +
                ", beta=" + beta +
                ", betaValue='" + betaValue + '\'' +
                ", betaMd5='" + betaMd5 + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", configBetaClientList=" + configBetaClientList +
                '}';
    }
}
