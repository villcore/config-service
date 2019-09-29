package com.duduyixia.config.client.internal;

import com.duduyixia.config.client.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * created by WangTao on 2019-09-19
 */
public class ConfigServiceContext {

    private static final Logger log = LoggerFactory.getLogger(ConfigServiceContext.class);

    private final ConfigServiceEnv configServiceEnv;

    private ConfigService configService;

    public ConfigServiceContext(ConfigServiceEnv configServiceEnv) {
        this.configServiceEnv = configServiceEnv;
    }

    public synchronized ConfigService createConfigService() {
        if (configService == null) {
            configService = new DefaultConfigService(configServiceEnv);
        }
        return configService;
    }
}
