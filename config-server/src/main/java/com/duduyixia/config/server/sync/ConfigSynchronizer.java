package com.duduyixia.config.server.sync;

import com.duduyixia.config.server.bean.ConfigKey;

/**
 * created by WangTao on 2019-10-18
 */
public interface ConfigSynchronizer {

    public void notifyConfigChanged(ConfigKey configKey);

    public void onConfigChanged(ConfigKey configKey);

}
