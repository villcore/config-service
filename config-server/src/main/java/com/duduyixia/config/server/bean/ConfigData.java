package com.duduyixia.config.server.bean;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ConfigData {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config_data.id
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config_data.namespace
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    private String namespace;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config_data.env
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    private String env;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config_data.app
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    private String app;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config_data.group
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    private String group;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config_data.config
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    private String config;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config_data.md5
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    private String md5;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config_data.mark_deleted
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    private Boolean markDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config_data.beta
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    private Boolean beta;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config_data.beta_md5
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    private String betaMd5;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config_data.update_time
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config_data.create_time
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config_data.value
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    private String value;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config_data.beta_value
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    private String betaValue;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config_data.id
     *
     * @return the value of config_data.id
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config_data.id
     *
     * @param id the value for config_data.id
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config_data.namespace
     *
     * @return the value of config_data.namespace
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config_data.namespace
     *
     * @param namespace the value for config_data.namespace
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace == null ? null : namespace.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config_data.env
     *
     * @return the value of config_data.env
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public String getEnv() {
        return env;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config_data.env
     *
     * @param env the value for config_data.env
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public void setEnv(String env) {
        this.env = env == null ? null : env.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config_data.app
     *
     * @return the value of config_data.app
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public String getApp() {
        return app;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config_data.app
     *
     * @param app the value for config_data.app
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public void setApp(String app) {
        this.app = app == null ? null : app.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config_data.group
     *
     * @return the value of config_data.group
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public String getGroup() {
        return group;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config_data.group
     *
     * @param group the value for config_data.group
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public void setGroup(String group) {
        this.group = group == null ? null : group.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config_data.config
     *
     * @return the value of config_data.config
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public String getConfig() {
        return config;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config_data.config
     *
     * @param config the value for config_data.config
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public void setConfig(String config) {
        this.config = config == null ? null : config.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config_data.md5
     *
     * @return the value of config_data.md5
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public String getMd5() {
        return md5;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config_data.md5
     *
     * @param md5 the value for config_data.md5
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public void setMd5(String md5) {
        this.md5 = md5 == null ? null : md5.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config_data.mark_deleted
     *
     * @return the value of config_data.mark_deleted
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public Boolean getMarkDeleted() {
        return markDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config_data.mark_deleted
     *
     * @param markDeleted the value for config_data.mark_deleted
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public void setMarkDeleted(Boolean markDeleted) {
        this.markDeleted = markDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config_data.beta
     *
     * @return the value of config_data.beta
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public Boolean getBeta() {
        return beta;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config_data.beta
     *
     * @param beta the value for config_data.beta
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public void setBeta(Boolean beta) {
        this.beta = beta;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config_data.beta_md5
     *
     * @return the value of config_data.beta_md5
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public String getBetaMd5() {
        return betaMd5;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config_data.beta_md5
     *
     * @param betaMd5 the value for config_data.beta_md5
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public void setBetaMd5(String betaMd5) {
        this.betaMd5 = betaMd5 == null ? null : betaMd5.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config_data.update_time
     *
     * @return the value of config_data.update_time
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config_data.update_time
     *
     * @param updateTime the value for config_data.update_time
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config_data.create_time
     *
     * @return the value of config_data.create_time
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config_data.create_time
     *
     * @param createTime the value for config_data.create_time
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config_data.value
     *
     * @return the value of config_data.value
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public String getValue() {
        return value;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config_data.value
     *
     * @param value the value for config_data.value
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config_data.beta_value
     *
     * @return the value of config_data.beta_value
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public String getBetaValue() {
        return betaValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config_data.beta_value
     *
     * @param betaValue the value for config_data.beta_value
     *
     * @mbggenerated Sun Oct 27 20:18:16 CST 2019
     */
    public void setBetaValue(String betaValue) {
        this.betaValue = betaValue == null ? null : betaValue.trim();
    }

    private List<ConfigBetaClient> configBetaClientList = Collections.emptyList();

    public List<ConfigBetaClient> getConfigBetaClientList() {
        return configBetaClientList;
    }

    public void setConfigBetaClientList(List<ConfigBetaClient> configBetaClientList) {
        this.configBetaClientList = configBetaClientList;
    }
}