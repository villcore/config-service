package com.duduyixia.config.server.service;

import com.duduyixia.config.server.bean.ConfigKey;

/**
 * created by WangTao on 2019-09-20
 */
public interface ConfigWatcherPurgatory {

    void watchConfig(ConfigKey configKey, Runnable callback, long timeoutMs);

    void tryComplete(ConfigKey configKey);
}
