package com.duduyixia.config.server.service;

import com.duduyixia.config.server.bean.ConfigData;
import com.duduyixia.config.server.bean.ConfigKey;
import com.duduyixia.config.server.dto.ConfigWatcherDTO;
import com.duduyixia.config.server.event.EventSources;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * created by WangTao on 2019-10-15
 */
public abstract class ConfigWatcherManager {

    protected ConfigManager configManager;
    protected DelayedOperationPurgatory<DelayedConfigChangedNotifyOperation> purgatory;

    public ConfigWatcherManager(ConfigManager configManager) {
        this.configManager = configManager;
        this.purgatory = new DelayedOperationPurgatory<>("config-watcher-purgatory");
        EventSources.getConfigChangeEventSource().subscribe(purgatory::checkAndComplete);
    }

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

    public void watchConfig(Map<ConfigKey, String> changedConfig, final Set<ConfigKey> betaConfigData, Map<ConfigKey, Boolean> clientBeta,
                            Consumer<List<ConfigKey>> configChangeAction, long timeoutMs) {
        changedConfig.forEach((configKey, md5) -> {
            watchConfig(configKey, md5, betaConfigData.contains(configKey), clientBeta.get(configKey), configChangeAction, timeoutMs);
        });
    }

    private void watchConfig(ConfigKey configKey, String md5, boolean isBeta, boolean isClientBeta, Consumer<List<ConfigKey>> configChangeAction, long timeoutMs) {
        purgatory.tryCompleteElseWatch(
                new DelayedConfigChangedNotifyOperation(configKey, isBeta, isClientBeta, md5, configChangeAction, timeoutMs),
                Collections.singletonList(configKey));
    }

    public void shutdown() {
        if (purgatory != null) {
            purgatory.shutdown();
        }
    }

    private class DelayedConfigChangedNotifyOperation extends DelayedOperation {

        private ConfigKey configKey;
        private boolean isBeta;
        private boolean isClientBeta;
        private String md5;
        private long delayMs;
        Consumer<List<ConfigKey>> configChangeAction;

        public DelayedConfigChangedNotifyOperation(ConfigKey configKey, boolean isBeta, boolean isClientBeta, String md5, Consumer<List<ConfigKey>> configChangeAction, long delayMs) {
            super(delayMs, null);
            this.configKey = configKey;
            this.isBeta = isBeta;
            this.isClientBeta = isClientBeta;
            this.md5 = md5;
            this.delayMs = delayMs;
            this.configChangeAction = configChangeAction;
        }

        @Override
        public void onExpiration() {
            configChangeAction.accept(Collections.emptyList());
        }

        @Override
        public void onComplete() {
            // ignore
        }

        @Override
        public boolean tryComplete() {
            ConfigData configData = configManager.getConfig(configKey);
            if (ConfigManager.isEmpty(configData)) {
                return false;
            }

            // beta -> beta
            if (isBeta) {
                // beta -> beta
                if (configData.getBeta() && !Objects.equals(configData.getBetaMd5(), md5)) {
                    configChangeAction.accept(Collections.singletonList(configKey));
                    return true;
                }

                // beta -> normal
                if (!configData.getBeta()) {
                    configChangeAction.accept(Collections.singletonList(configKey));
                    return true;
                }
                return false;
            } else {
                // normal -> normal
                if (!configData.getBeta() && !Objects.equals(configData.getMd5(), md5)) {
                    configChangeAction.accept(Collections.singletonList(configKey));
                    return true;
                }

                // normal -> beta
                if (configData.getBeta()) {
                    configChangeAction.accept(Collections.singletonList(configKey));
                    return true;
                }
            }
            return false;
        }
    }
}
