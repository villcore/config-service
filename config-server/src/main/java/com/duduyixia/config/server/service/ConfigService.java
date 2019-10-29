package com.duduyixia.config.server.service;

import com.duduyixia.config.server.bean.ConfigBetaClient;
import com.duduyixia.config.server.bean.ConfigData;
import com.duduyixia.config.server.bean.ConfigKey;
import com.duduyixia.config.server.dto.ConfigDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

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
     * @param configKey
     * @param clientIp
     * @return
     */
    public ConfigDataDTO getConfig(ConfigKey configKey, String clientIp) {
        ConfigData configData = configManager.getConfig(configKey);
        if (ConfigManager.isEmpty(configData) || configData.getMarkDeleted()) {
            return createDeletedConfigDTO();
        }

        boolean isClientBeta = isClientBeta(clientIp, configData);
        return createConfigDTO(configData, isClientBeta);
    }

    private boolean isClientBeta(String clientIp, ConfigData configData) {
        if (!configData.getBeta()) {
            return false;
        }

        List<ConfigBetaClient> configBetaClientList = configData.getConfigBetaClientList();
        if (configBetaClientList == null || configBetaClientList.isEmpty()) {
            return false;
        }

        for (ConfigBetaClient betaIp : configBetaClientList) {
            if (Objects.equals(betaIp.getIp(), clientIp)) {
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
        configDataDTO.setMarkDeleted(configData.getMarkDeleted());
        configDataDTO.setMd5(configData.getMd5());
        configDataDTO.setValue(configData.getConfigValue());
        return configDataDTO;
    }

    public void watchConfig(Map<ConfigKey, String> configMd5, String clientIp, long timeoutMs, Consumer<List<ConfigKey>> configChangeAction) {
        clientWatcherManager.reportClientConfig(configMd5, clientIp);

        Map<ConfigKey, ConfigDataDTO> changedConfigData = new HashMap<>();
        Set<ConfigKey> betaConfigData = new HashSet<>();
        AtomicBoolean allDeleted = new AtomicBoolean(true);

        configMd5.forEach((k, v) -> {
            ConfigData configData = configManager.getConfig(k);
            if (configData.getBeta()) {
                boolean clientBeta = false;
                List<ConfigBetaClient> betaIps = configData.getConfigBetaClientList();
                for (ConfigBetaClient configBetaClient : betaIps) {
                    if (configBetaClient.getIp().equals(clientIp)) {
                        clientBeta = true;
                        break;
                    }
                }

                if (clientBeta && !Objects.equals(configData.getBetaMd5(), v)) {
                    if (configData.getMarkDeleted()) {
                        changedConfigData.put(k, createDeletedConfigDTO());
                    } else {
                        allDeleted.set(false);
                        changedConfigData.put(k, createConfigDTO(configData, true));
                    }
                }
                betaConfigData.add(k);
            } else {
                if (!Objects.equals(configData.getMd5(), v)) {
                    if (configData.getMarkDeleted()) {
                        changedConfigData.put(k, createDeletedConfigDTO());
                    } else {
                        allDeleted.set(false);
                        changedConfigData.put(k, createConfigDTO(configData, false));
                    }
                }
            }
        });

        List<ConfigKey> changedConfig = new ArrayList<>(changedConfigData.keySet());
        if (changedConfigData.isEmpty() || allDeleted.get()) {
            clientWatcherManager.watchConfig(configMd5, betaConfigData, configChangeAction, timeoutMs);
        } else {
            configChangeAction.accept(changedConfig);
        }
    }
}
