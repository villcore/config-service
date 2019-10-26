package com.duduyixia.config.server.service;

import com.duduyixia.config.server.bean.ConfigData;
import com.duduyixia.config.server.bean.ConfigKey;
import com.duduyixia.config.server.dao.ConfigDataMapper;
import com.duduyixia.config.server.dto.ClientConfigInfo;
import com.duduyixia.config.server.dto.ConfigWatcherDTO;
import com.duduyixia.config.server.event.EventSources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * created by WangTao on 2019-09-20
 */
@Service
public class ConfigAdminService {

    private static final Logger log = LoggerFactory.getLogger(ConfigAdminService.class);

    @Resource
    private RedisConfigWatcherManager configWatcherManager;

    @Resource
    private ConfigManager configManager;

    public ConfigWatcherDTO listConfigClient(ConfigKey configKey) {
        return configWatcherManager.getConfigClient(configKey);
    }

    public Object publishConfig(ConfigKey configKey, String configValue) {
        ConfigData configData = new ConfigData();

        EventSources.getConfigChangeEventSource().publish(configKey);
        return null;
    }
}
