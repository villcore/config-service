package com.duduyixia.config.server.bean;

import java.io.Serializable;
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
    private long updateTimeMs;

    private boolean isBeta;
    private String betaIps;
    private String betaValue;
    private String betaMd5;

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

    public long getUpdateTimeMs() {
        return updateTimeMs;
    }

    public void setUpdateTimeMs(long updateTimeMs) {
        this.updateTimeMs = updateTimeMs;
    }

    public boolean isBeta() {
        return isBeta;
    }

    public void setBeta(boolean beta) {
        isBeta = beta;
    }

    public String getBetaIps() {
        return betaIps;
    }

    public void setBetaIps(String betaIps) {
        this.betaIps = betaIps;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigData that = (ConfigData) o;
        return markDeleted == that.markDeleted &&
                updateTimeMs == that.updateTimeMs &&
                isBeta == that.isBeta &&
                Objects.equals(id, that.id) &&
                Objects.equals(namespace, that.namespace) &&
                Objects.equals(env, that.env) &&
                Objects.equals(app, that.app) &&
                Objects.equals(group, that.group) &&
                Objects.equals(config, that.config) &&
                Objects.equals(value, that.value) &&
                Objects.equals(md5, that.md5) &&
                Objects.equals(betaIps, that.betaIps) &&
                Objects.equals(betaValue, that.betaValue) &&
                Objects.equals(betaMd5, that.betaMd5);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, namespace, env, app, group, config, value, md5, markDeleted, updateTimeMs, isBeta, betaIps, betaValue, betaMd5);
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
                ", updateTimeMs=" + updateTimeMs +
                ", isBeta=" + isBeta +
                ", betaIps='" + betaIps + '\'' +
                ", betaValue='" + betaValue + '\'' +
                ", betaMd5='" + betaMd5 + '\'' +
                '}';
    }
}
