package com.duduyixia.config.client;

import com.duduyixia.config.client.internal.ConfigServiceContext;
import com.duduyixia.config.client.internal.ConfigServiceEnv;

/**
 * created by WangTao on 2019-09-19
 */
public class ConfigServiceFactory {

    private static ConfigServiceContext configServiceContext;

    static {
       ConfigServiceEnv configServiceEnv = ConfigServiceEnv.load();
       configServiceContext = new ConfigServiceContext(configServiceEnv);
    }

    public static ConfigService createConfigService() {
        return configServiceContext.createConfigService();
    }
}
