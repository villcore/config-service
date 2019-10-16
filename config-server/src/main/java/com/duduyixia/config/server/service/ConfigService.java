package com.duduyixia.config.server.service;

import com.duduyixia.config.server.bean.ConfigData;
import com.duduyixia.config.server.bean.ConfigKey;
import com.duduyixia.config.server.dto.ConfigDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * created by WangTao on 2019-09-20
 */
@Service
public class ConfigService {

    private static final Logger log = LoggerFactory.getLogger(ConfigService.class);

    @Resource
    private ConfigManager configManager;

    @Resource
    private ConfigWatcherManager clientWatcherManager;

    /**
     *
     * @param configKey
     * @param clientIp
     * @return
     */
    public ConfigDataDTO getConfig(ConfigKey configKey, String clientIp) {
        ConfigData configData = configManager.getConfig(configKey);
        if (configData.isMarkDeleted()) {
            return createDeletedConfigDTO();
        }

        boolean isClientBeta = isClientBeta(clientIp, configData);
        return createConfigDTO(configData, isClientBeta);
    }

    private boolean isClientBeta(String clientIp, ConfigData configData) {
        if (!configData.isBeta()) {
            return false;
        }

        String betaIps = configData.getBetaIps();
        if (betaIps == null || betaIps.trim().isEmpty()) {
            return false;
        }

        String[] betaIpSplits = betaIps.split(",");
        if (betaIpSplits.length == 0) {
            return false;
        }

        for (String betaIp : betaIpSplits) {
            if (betaIp.equalsIgnoreCase(clientIp)) {
                return true;
            }
        }
        return false;
    }

    private ConfigDataDTO createDeletedConfigDTO() {
        ConfigDataDTO configDataDTO = new ConfigDataDTO();
        configDataDTO.setMarkDeleted(true);
        return configDataDTO;
    }

    private ConfigDataDTO createConfigDTO(ConfigData configData, boolean isClientBeta) {
        ConfigDataDTO configDataDTO = new ConfigDataDTO();
        configDataDTO.setBeta(isClientBeta);
        configDataDTO.setMarkDeleted(configData.isMarkDeleted());
        configDataDTO.setMd5(configData.getMd5());
        configDataDTO.setValue(configData.getValue());
        return configDataDTO;
    }

    /**
     *
     * @param configMd5
     * @param clientIp
     * @return
     */
    public Map<ConfigKey, ConfigDataDTO> watchConfig(Map<ConfigKey, String> configMd5, String clientIp) {
        clientWatcherManager.reportClientConfig(configMd5, clientIp);

        Map<ConfigKey, ConfigDataDTO> changedConfigData = new HashMap<>();
        configMd5.forEach((k, v) -> {
            ConfigData configData = configManager.getConfig(k);
            if (!Objects.equals(configData.getMd5(), v)) {
                if (configData.isMarkDeleted()) {
                    changedConfigData.put(k, createDeletedConfigDTO());
                } else {
                    changedConfigData.put(k, createConfigDTO(configData, false));
                }
            }
        });

        if (!changedConfigData.isEmpty()) {
            // put into timeWheel
            // define a callback function
        }
        // check md5 version, if modifyed, get and return
    }
}
