package com.duduyixia.config.server.service;

import com.duduyixia.config.common.util.MD5;
import com.duduyixia.config.server.bean.ConfigData;
import com.duduyixia.config.server.bean.ConfigKey;
import com.duduyixia.config.server.dao.ConfigDataMapper;
import com.duduyixia.config.server.dto.ConfigWatcherDTO;
import com.duduyixia.config.server.event.EventSources;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

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

    @Resource
    private ConfigDataMapper configDataMapper;

    public ConfigWatcherDTO listConfigClient(ConfigKey configKey) {
        return configWatcherManager.getConfigClient(configKey);
    }

    public boolean publishConfig(ConfigKey configKey, String configValue) {
        boolean publishSuccess = tryPublishConfig(configKey, configValue);
        log.info("Publish config [{}]:[{}] result {}", configKey, configValue, publishSuccess);
        if (publishSuccess) {
            EventSources.getConfigChangeEventSource().publish(configKey);
        }
        return publishSuccess;
    }

    private boolean tryPublishConfig(ConfigKey configKey, String configValue) {
        ConfigData configData = createConfigData(configKey, configValue);
        return configDataMapper.insertConfig(configData) > 0;
    }

    private ConfigData createConfigData(ConfigKey configKey, String configValue) {
        ConfigData configData = new ConfigData();
        configData.setNamespace(configKey.getNamespace());
        configData.setEnv(configKey.getEnv());
        configData.setApp(configKey.getApp());
        configData.setGroup(configKey.getGroup());
        configData.setConfig(configKey.getConfig());
        configData.setValue(configValue);
        configData.setMd5(MD5.getInstance().getMD5String(configValue));
        configData.setUpdateTime(new Date());
        if (StringUtils.isBlank(configKey.getGroup())) {
            configData.setGroup("default");
        }
        return configData;
    }

    public Boolean updateConfig(Integer configId, boolean beta, String configValue) {
        ConfigData configData = configDataMapper.getConfig(configId);
        if (configData != null) {
            configData.setValue(configValue);
            configData.setUpdateTime(new Date());
            return configDataMapper.updateConfig(configData);
        }
        return false;
    }
}
