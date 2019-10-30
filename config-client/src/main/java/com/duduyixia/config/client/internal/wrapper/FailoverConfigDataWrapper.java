package com.duduyixia.config.client.internal.wrapper;

import com.duduyixia.config.client.ConfigException;
import com.duduyixia.config.client.internal.ConfigServiceEnv;
import com.duduyixia.config.client.internal.config.ConfigData;
import com.duduyixia.config.client.internal.config.ConfigKey;
import com.duduyixia.config.client.internal.executor.ConfigTaskExecutor;
import com.duduyixia.config.client.internal.http.ConfigHttpClient;
import com.duduyixia.config.client.internal.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

/**
 * created by WangTao on 2019-09-21
 */
public class FailoverConfigDataWrapper extends DefaultConfigDataWrapper {

    private static Logger log = LoggerFactory.getLogger(FailoverConfigDataWrapper.class);

    private static final String CHARSET = "utf-8";

    private Path configFilePath;
    private boolean configSaved;
    private long lastSaved;

    FailoverConfigDataWrapper(ConfigData configData, ConfigServiceEnv configServiceEnv,
                              ConfigTaskExecutor configTaskExecutor, ConfigHttpClient configHttpClient) {
        super(configData, configServiceEnv, configTaskExecutor, configHttpClient);
        configFilePath = getConfigFilePath(configServiceEnv, configData);
        configSaved = false;
    }

    private Path getConfigFilePath(ConfigServiceEnv configServiceEnv, ConfigData configData) {
        String group = configData.getGroup();
        String config = configData.getConfig();
        if (group == null || group.trim().length() == 0) {
            group = ConfigServiceEnv.DEFAULT_GROUP;
        }

        return Paths.get(
                configServiceEnv.getConfigFileDir(),
                configServiceEnv.getApp(),
                configServiceEnv.getEnv(),
                configServiceEnv.getNamespace(),
                group,
                config
        );
    }

    @Override
    public ConfigData getConfigData() throws ConfigException {

        // read from server
        ConfigData configDataTmp = null;
        try {
            configDataTmp = super.getConfigData();
        } catch (ConfigException e) {
            log.error("Get config [{}] from server failed, read local config file", ConfigKey.toFlatKey(configData.getConfigKey()), e);
        }

        // read from local
        if (configDataTmp == null) {
            configDataTmp = readConfigFile();
            tryUpdateConfig(configDataTmp);
        }

        if (!configSaved) {
            doSaveConfig();
            configSaved = true;
        }

        return getConfigDataSnapshot();
    }

    private void doSaveConfig() {
        long now = System.currentTimeMillis();
        if (now - lastSaved >= ConfigServiceEnv.CONFIG_SAVED_INTERVAL_MS) {
            lastSaved = now;
            configTaskExecutor.execute(this::saveConfig);
        }
    }

    private void saveConfig() {
        File configFile = configFilePath.toFile();
        String configFileRoot = configFile.getParent();
        File configFileRootDir = new File(configFileRoot);

        // check and create config root dir
        try {
            if (!configFileRootDir.exists() && !configFileRootDir.mkdirs()) {
                log.warn("Create config file dir [{}] failed", configFileRootDir);
                return;
            }
        } catch (SecurityException e) {
            log.error("Create config file dir [{}] error", configFileRootDir, e);
            return;
        }

        // check and create config file
        try {
            if (!configFile.exists() && !configFile.createNewFile()) {
                log.warn("Create config file [{}] failed", configFile);
                return;
            }
        } catch (Exception e) {
            log.warn("Create config file [{}] error", configFile);
        }

        ConfigData configData = getConfigDataSnapshot();
        Path configTmpPath = Paths.get(configFileRoot, configData.getConfig() + ".tmp");
        Path targetConfigPath = configFilePath;
        try {
            String configJson = JsonUtil.toJson(getConfigDataSnapshot());
            Files.write(configTmpPath, configJson.getBytes(CHARSET), StandardOpenOption.CREATE_NEW, StandardOpenOption.TRUNCATE_EXISTING);

            try {
                Files.move(configTmpPath, targetConfigPath, StandardCopyOption.ATOMIC_MOVE);
            } catch (IOException e) {
                try {
                    Files.move(configTmpPath, targetConfigPath, StandardCopyOption.REPLACE_EXISTING);
                    log.debug("Non-atomic move of {} to {} succeeded after atomic move failed due to {}", configTmpPath, targetConfigPath,
                            e.getMessage());
                } catch (IOException inner) {
                    inner.addSuppressed(e);
                    throw inner;
                }
            }
        } catch (Exception e) {
            log.error("Write config tmp file [{}] error", targetConfigPath, e);
        }
        log.info("Save config [{}] to file [{}]", configData.getConfigKey(), configFilePath);
    }

    private ConfigData readConfigFile() throws ConfigException {
        try {
            if (!Files.exists(configFilePath)) {
                throw new ConfigException("Config " + configFilePath + " not exist");
            }
        } catch (Exception e) {
            throw new ConfigException(e);
        }

        String configDataJson;
        try {
            byte[] bytes = Files.readAllBytes(configFilePath);
            configDataJson = new String(bytes, CHARSET);
        } catch (Exception e) {
            throw new ConfigException(e);
        }

        try {
            return JsonUtil.fromJson(configDataJson, ConfigData.class);
        } catch (Exception e) {
            throw new ConfigException(e);
        } finally {
            log.info("Read config [{}] from file [{}]", configData.getConfigKey(), configFilePath);
        }
    }

    @Override
    public void configMarkDeleted(ConfigData configData, ConfigData newConfigData) {
        super.configMarkDeleted(configData, newConfigData);
        try {
            Files.delete(configFilePath);
        } catch (Exception e) {
            log.error("Delete config [{}] error", configData.getConfigKey(), e);
        }
    }

    @Override
    public void configChanged(ConfigData configData, ConfigData newConfigData) {
        super.configChanged(configData, newConfigData);
        // update config
        saveConfig();
    }
}
