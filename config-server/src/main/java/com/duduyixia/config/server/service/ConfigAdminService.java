package com.duduyixia.config.server.service;

import com.duduyixia.config.common.util.MD5;
import com.duduyixia.config.server.bean.ConfigBetaClient;
import com.duduyixia.config.server.bean.ConfigData;
import com.duduyixia.config.server.bean.ConfigKey;
import com.duduyixia.config.server.dao.ConfigBetaClientMapper;
import com.duduyixia.config.server.dao.rds.mapper.ConfigDataMapper;
import com.duduyixia.config.server.dto.ConfigWatcherDTO;
import com.duduyixia.config.server.event.EventSources;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
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

    @Resource
    private ConfigDataMapper configDataMapper;

    @Resource
    private ConfigBetaClientMapper configBetaClientMapper;

    public ConfigWatcherDTO listConfigClient(ConfigKey configKey) {
        return configWatcherManager.getConfigClient(configKey);
    }

    public boolean publishConfig(ConfigKey configKey, String configValue, boolean beta, List<String> clientIp) {
        boolean publishSuccess = tryPublishConfig(configKey, configValue, beta, clientIp);
        log.info("Publish config [{}]:[{}] result {}", configKey, configValue, publishSuccess);
        if (publishSuccess) {
            EventSources.getConfigChangeEventSource().publish(configKey);
        }
        return publishSuccess;
    }

    private boolean tryPublishConfig(ConfigKey configKey, String configValue, boolean beta, List<String> clientIp) {
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

    public Boolean updateConfig(Integer configId, boolean beta, String configValue, List<String> betaClientIp) {
        ConfigData configData = configDataMapper.getConfig(configId);
        if (configData == null) {
            return false;
        }

        boolean updateSuccess = tryUpdateConfig(beta, configValue, configData, betaClientIp);
        if (updateSuccess) {
            EventSources.getConfigChangeEventSource().publish(ConfigKey.valueOf(configData));
        }
        return updateSuccess;
    }

    private Boolean tryUpdateConfig(boolean beta, String configValue, ConfigData configData, List<String> betaClientIp) {
        // 如果beta切换为线上
        // 清理所有beta client
        if (!beta) {
            configData.setValue(configValue);
            configData.setMd5(MD5.getInstance().getMD5String(configValue));
            configData.setBeta(false);
            configData.setUpdateTime(new Date());
            boolean succeed = configDataMapper.updateConfig(configData);
            if (succeed) {

            }
            return succeed;
        } else {
            configData.setBetaValue(configValue);
            configData.setBeta(true);
            configData.setBetaMd5(MD5.getInstance().getMD5String(configValue));
            configData.setUpdateTime(new Date());
            return configDataMapper.updateConfig(configData);
        }
    }
}
