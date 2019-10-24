package com.duduyixia.config.server.service;

import com.duduyixia.config.server.bean.ConfigKey;
import com.duduyixia.config.server.dto.ConfigWatcherDTO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * created by WangTao on 2019-10-15
 */
public abstract class ConfigWatcherManager {

    public void reportClientConfig(Map<ConfigKey, String> configMd5, final String clientIp) {
        configMd5.forEach((k, v) -> {
            reportClientConfig(k, v, clientIp);
        });
    }

    public abstract void reportClientConfig(ConfigKey configKey, String configMd5, String clientIp);


    public List<ConfigWatcherDTO> getConfigClient(List<ConfigKey> configKeys) {
        return configKeys.stream()
                .map(this::getConfigClient)
                .collect(Collectors.toList());
    }

    public abstract ConfigWatcherDTO getConfigClient(ConfigKey configKey);


}
