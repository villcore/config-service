package com.villcore.config.server.bean;

import java.util.Date;

public class ConfigBetaClient {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config_beta_client.id
     *
     * @mbggenerated Sun Oct 27 20:15:34 CST 2019
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config_beta_client.config_id
     *
     * @mbggenerated Sun Oct 27 20:15:34 CST 2019
     */
    private Integer configId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config_beta_client.ip
     *
     * @mbggenerated Sun Oct 27 20:15:34 CST 2019
     */
    private String ip;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config_beta_client.exist
     *
     * @mbggenerated Sun Oct 27 20:15:34 CST 2019
     */
    private Boolean exist;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config_beta_client.create_time
     *
     * @mbggenerated Sun Oct 27 20:15:34 CST 2019
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config_beta_client.update_time
     *
     * @mbggenerated Sun Oct 27 20:15:34 CST 2019
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config_beta_client.id
     *
     * @return the value of config_beta_client.id
     *
     * @mbggenerated Sun Oct 27 20:15:34 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config_beta_client.id
     *
     * @param id the value for config_beta_client.id
     *
     * @mbggenerated Sun Oct 27 20:15:34 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config_beta_client.config_id
     *
     * @return the value of config_beta_client.config_id
     *
     * @mbggenerated Sun Oct 27 20:15:34 CST 2019
     */
    public Integer getConfigId() {
        return configId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config_beta_client.config_id
     *
     * @param configId the value for config_beta_client.config_id
     *
     * @mbggenerated Sun Oct 27 20:15:34 CST 2019
     */
    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config_beta_client.ip
     *
     * @return the value of config_beta_client.ip
     *
     * @mbggenerated Sun Oct 27 20:15:34 CST 2019
     */
    public String getIp() {
        return ip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config_beta_client.ip
     *
     * @param ip the value for config_beta_client.ip
     *
     * @mbggenerated Sun Oct 27 20:15:34 CST 2019
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config_beta_client.exist
     *
     * @return the value of config_beta_client.exist
     *
     * @mbggenerated Sun Oct 27 20:15:34 CST 2019
     */
    public Boolean getExist() {
        return exist;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config_beta_client.exist
     *
     * @param exist the value for config_beta_client.exist
     *
     * @mbggenerated Sun Oct 27 20:15:34 CST 2019
     */
    public void setExist(Boolean exist) {
        this.exist = exist;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config_beta_client.create_time
     *
     * @return the value of config_beta_client.create_time
     *
     * @mbggenerated Sun Oct 27 20:15:34 CST 2019
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config_beta_client.create_time
     *
     * @param createTime the value for config_beta_client.create_time
     *
     * @mbggenerated Sun Oct 27 20:15:34 CST 2019
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config_beta_client.update_time
     *
     * @return the value of config_beta_client.update_time
     *
     * @mbggenerated Sun Oct 27 20:15:34 CST 2019
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config_beta_client.update_time
     *
     * @param updateTime the value for config_beta_client.update_time
     *
     * @mbggenerated Sun Oct 27 20:15:34 CST 2019
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}