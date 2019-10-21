package com.duduyixia.config.server.service;

import com.duduyixia.config.server.bean.ConfigKey;

public class DelayedConfigWatcherPugatoryImpl implements ConfigWatcherPurgatory {

    // TODO: use timeWheel

    @Override
    public void watchConfig(ConfigKey configKey, Runnable callback, long timeoutMs) {

    }

    @Override
    public void tryComplete(ConfigKey configKey) {

    }
}
