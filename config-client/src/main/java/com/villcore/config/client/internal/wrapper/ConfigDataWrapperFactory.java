package com.villcore.config.client.internal.wrapper;

import com.villcore.config.client.internal.ConfigServiceEnv;
import com.villcore.config.client.internal.config.ConfigData;
import com.villcore.config.client.internal.executor.ConfigTaskExecutor;
import com.villcore.config.client.internal.http.ConfigHttpClient;

/**
 * created by WangTao on 2019-09-22
 */
public class ConfigDataWrapperFactory {

    public static DefaultConfigDataWrapper createConfigDataWrapper(
            ConfigServiceEnv configServiceEnv, ConfigData configData, ConfigTaskExecutor configTaskExecutor, ConfigHttpClient configHttpClient) {
        DefaultConfigDataWrapper configDataWrapper = new DefaultConfigDataWrapper(configData, configServiceEnv, configTaskExecutor, configHttpClient);
        configTaskExecutor.addConfigWrapper(configDataWrapper);
        return configDataWrapper;
    }

    public static DefaultConfigDataWrapper createFailoverConfigDataWrapper(
            ConfigServiceEnv configServiceEnv, ConfigData configData, ConfigTaskExecutor configTaskExecutor, ConfigHttpClient configHttpClient) {
        FailoverConfigDataWrapper configDataWrapper = new FailoverConfigDataWrapper(configData, configServiceEnv, configTaskExecutor, configHttpClient);
        configTaskExecutor.addConfigWrapper(configDataWrapper);
        return configDataWrapper;
    }
}
