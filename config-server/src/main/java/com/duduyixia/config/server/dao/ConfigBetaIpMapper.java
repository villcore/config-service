package com.duduyixia.config.server.dao;

import com.duduyixia.config.server.bean.ConfigBetaIp;
import com.duduyixia.config.server.bean.ConfigData;

import java.util.List;

/**
 * created by WangTao on 2019-10-23
 */
public interface ConfigBetaIpMapper {

    public List<ConfigBetaIp> getBetaIps(ConfigData configData);
}
