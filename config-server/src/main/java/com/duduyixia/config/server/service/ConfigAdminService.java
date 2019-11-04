package com.duduyixia.config.server.service;

import com.duduyixia.config.common.util.MD5;
import com.duduyixia.config.server.bean.*;
import com.duduyixia.config.server.dao.rds.mapper.ConfigBetaClientMapper;
import com.duduyixia.config.server.dao.rds.mapper.ConfigDataMapper;
import com.duduyixia.config.server.dto.ConfigWatcherDTO;
import com.duduyixia.config.server.event.EventSources;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * created by WangTao on 2019-09-20
 */
@Service
public class ConfigAdminService {

    private static final Logger log = LoggerFactory.getLogger(ConfigAdminService.class);

    @Resource
    private RedisConfigWatcherManager configWatcherManager;

    @Resource
    private ConfigDataMapper configDataMapper;

    @Resource
    private ConfigBetaClientMapper configBetaClientMapper;

    public ConfigWatcherDTO listConfigClient(ConfigKey configKey) {
        return configWatcherManager.getConfigClient(configKey);
    }

    /**
     *
     * @param configKey
     * @param configValue
     * @return
     */
    public boolean publishConfig(ConfigKey configKey, String configValue) {
        boolean publishSuccess = tryPublishConfig(configKey, configValue);
        log.info("Publish config [{}]:[{}] result {}", configKey, configValue, publishSuccess);
        if (publishSuccess) {
            EventSources.getConfigPublishEventSource().publish(configKey);
        }
        return publishSuccess;
    }

    private boolean tryPublishConfig(ConfigKey configKey, String configValue) {
        ConfigData configData = getConfig(configKey);
        if (configData != null) {
            // already exist a same config
            return false;
        }

        configData = createConfigData(configKey, configValue);
        return configDataMapper.insertSelective(configData) > 0;
    }

    private ConfigData getConfig(ConfigKey configKey) {
        ConfigDataExample example = new ConfigDataExample();
        example.createCriteria()
                .andNamespaceEqualTo(configKey.getNamespace())
                .andEnvEqualTo(configKey.getEnv())
                .andAppEqualTo(configKey.getApp())
                .andConfigGroupEqualTo(configKey.getGroup())
                .andConfigEqualTo(configKey.getConfig());

        List<ConfigData> configDataList = configDataMapper.selectByExampleWithBLOBs(example);
        if (CollectionUtils.isEmpty(configDataList)) {
            return null;
        } else {
            return configDataList.get(0);
        }
    }

    public ConfigData getConfig(Integer configId) {
        return configDataMapper.selectByPrimaryKey(configId);
    }

    private ConfigData createConfigData(ConfigKey configKey, String configValue) {
        ConfigData configData = new ConfigData();
        configData.setNamespace(configKey.getNamespace());
        configData.setEnv(configKey.getEnv());
        configData.setApp(configKey.getApp());
        configData.setConfigGroup(configKey.getGroup());
        configData.setConfig(configKey.getConfig());
        configData.setConfigValue(configValue);
        configData.setMd5(MD5.getInstance().getMD5String(configValue));
        configData.setUpdateTime(new Date());
        if (StringUtils.isBlank(configKey.getGroup())) {
            configData.setConfigGroup("default");
        }
        return configData;
    }

    public Boolean updateConfig(Integer configId, boolean beta, String configValue, List<String> betaClientIp) {
        ConfigData configData = getConfig(configId);
        if (configData == null) {
            return false;
        }

        boolean updateSuccess = tryUpdateConfig(configData, beta, configValue, betaClientIp);
        if (updateSuccess) {
            log.info("Update config {} success ", configData.getId());
            EventSources.getConfigPublishEventSource().publish(ConfigKey.valueOf(configData));
            EventSources.getConfigChangeEventSource().publish(ConfigKey.valueOf(configData));
        }
        return updateSuccess;
    }

    private Boolean tryUpdateConfig(ConfigData configData, boolean beta, String configValue, List<String> betaClientIp) {
        // beta config
        if (configData.getBeta()) {
            if (beta) {
                // beta -> beta
                ConfigBetaClientExample example = new ConfigBetaClientExample();
                example.createCriteria().andConfigIdEqualTo(configData.getId());
                List<ConfigBetaClient> configBetaClients = configBetaClientMapper.selectByExample(example);
                configBetaClients = configBetaClients.stream()
                        .filter(client -> !betaClientIp.contains(client.getIp()))
                        .collect(Collectors.toList());
                configBetaClients.forEach(client -> {
                    configBetaClientMapper.deleteByPrimaryKey(client.getId());
                });
                return updateBetaConfig(configData, configValue, betaClientIp);
            } else {
                // beta -> normal
                ConfigBetaClientExample example = new ConfigBetaClientExample();
                example.createCriteria().andConfigIdEqualTo(configData.getId());
                List<ConfigBetaClient> configBetaClients = configBetaClientMapper.selectByExample(example);
                configBetaClients.forEach(client -> {
                    configBetaClientMapper.deleteByPrimaryKey(client.getId());
                });

                configData.setConfigValue(configValue);
                configData.setMd5(MD5.getInstance().getMD5String(configValue));
                configData.setBeta(false);
                configData.setUpdateTime(new Date());
                return configDataMapper.updateByPrimaryKeyWithBLOBs(configData) > 0;
            }
        } else {
            if (beta) {
                // normal -> beta
                return updateBetaConfig(configData, configValue, betaClientIp);
            } else {
                // normal -> normal
                configData.setConfigValue(configValue);
                configData.setMd5(MD5.getInstance().getMD5String(configValue));
                configData.setBeta(false);
                configData.setUpdateTime(new Date());
                return configDataMapper.updateByPrimaryKeyWithBLOBs(configData) > 0;
            }
        }
    }

    private Boolean updateBetaConfig(ConfigData configData, String configValue, List<String> betaClientIp) {
        betaClientIp.forEach(ip -> {
            ConfigBetaClient betaClient = new ConfigBetaClient();
            betaClient.setConfigId(configData.getId());
            betaClient.setExist(true);
            betaClient.setIp(ip);
            betaClient.setUpdateTime(new Date());
            configBetaClientMapper.insertSelective(betaClient);
        });

        configData.setBetaConfigValue(configValue);
        configData.setBeta(true);
        configData.setBetaMd5(MD5.getInstance().getMD5String(configValue));
        configData.setUpdateTime(new Date());
        return configDataMapper.updateByPrimaryKeyWithBLOBs(configData) > 0;
    }

    public Boolean deleteConfig(ConfigKey configKey) {
        ConfigData configData = getConfig(configKey);
        if (configData == null) {
            return false;
        }

        if (configData.getBeta()) {
            ConfigBetaClientExample example = new ConfigBetaClientExample();
            example.createCriteria().andConfigIdEqualTo(configData.getId());
            configBetaClientMapper.deleteByExample(example);
        }
        return configDataMapper.deleteByPrimaryKey(configData.getId()) > 0;
    }
}
