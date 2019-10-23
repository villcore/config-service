package com.duduyixia.config.server.bean;

import java.io.Serializable;
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
    private long updateTimeMs;

    private boolean isBeta;
    private String betaValue;
    private String betaMd5;

    private List<ConfigBetaIp> configBetaIpList;

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

    public List<ConfigBetaIp> getConfigBetaIpList() {
        return configBetaIpList;
    }

    public void setConfigBetaIpList(List<ConfigBetaIp> configBetaIpList) {
        this.configBetaIpList = configBetaIpList;
    }
}
