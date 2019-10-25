package com.duduyixia.config.server.service;

import com.duduyixia.config.server.bean.ConfigData;
import com.duduyixia.config.server.bean.ConfigKey;
import com.duduyixia.config.server.dto.ConfigWatcherDTO;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * created by WangTao on 2019-10-15
 */
public abstract class ConfigWatcherManager {

    protected ConfigManager configManager;
    protected DelayedOperationPurgatory<DelayedConfigChangedNotifyOperation> purgatory;

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

    public void watchConfig(Map<ConfigKey, String> changedConfig, final Set<ConfigKey> betaConfigData,
                            Consumer<List<ConfigKey>> configChangeAction, long timeoutMs) {
        changedConfig.forEach((configKey, md5) -> {
            watchConfig(configKey, md5, betaConfigData.contains(configKey), configChangeAction, timeoutMs);
        });
    }

    private void watchConfig(ConfigKey configKey, String md5, boolean isBeta, Consumer<List<ConfigKey>> configChangeAction, long timeoutMs) {

    }

    private class DelayedConfigChangedNotifyOperation extends DelayedOperation {

        private ConfigKey configKey;
        private boolean isBeta;
        private String md5;
        private long delayMs;
        private Runnable action;

        public DelayedConfigChangedNotifyOperation(long delayMs, Lock lock, ConfigKey configKey, boolean isBeta,
                                                   String md5, long delayMs1, Runnable action) {
            super(delayMs, lock);
            this.configKey = configKey;
            this.isBeta = isBeta;
            this.md5 = md5;
            this.delayMs = delayMs1;
            this.action = action;
        }

        @Override
        public void onExpiration() {

        }

        @Override
        public void onComplete() {
            action.run();
        }

        @Override
        public boolean tryComplete() {
            ConfigData configData = configManager.getConfig(configKey);
            if (ConfigManager.isEmpty(configData)) {
                return true;
            }

            if (configData.isBeta() && !Objects.equals(configData.getBetaMd5(), md5)) {
                return true;
            }

            if (!Objects.equals(configData.getMd5(), md5)) {
                return true;
            }
            return false;
        }
    }
}
