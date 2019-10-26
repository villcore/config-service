package com.duduyixia.config.server.dao;

import com.duduyixia.config.server.bean.ConfigData;
import com.duduyixia.config.server.bean.ConfigKey;
import org.springframework.stereotype.Repository;

/**
 * created by WangTao on 2019-10-23
 */
@Repository
public interface ConfigDataMapper {

    public ConfigData getConfig(ConfigKey configKey);

    public int insertConfig(ConfigData configData);
}
