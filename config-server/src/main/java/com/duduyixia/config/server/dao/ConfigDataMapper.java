package com.duduyixia.config.server.dao;

import com.duduyixia.config.server.bean.ConfigData;
import com.duduyixia.config.server.bean.ConfigKey;

/**
 * created by WangTao on 2019-10-23
 */
public interface ConfigDataMapper {

    public ConfigData getConfig(ConfigKey configKey);
}
