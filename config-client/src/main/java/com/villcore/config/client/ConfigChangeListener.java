package com.villcore.config.client;

/**
 * created by WangTao on 2019-09-16
 */
public interface ConfigChangeListener extends Listener{

    void onChange(String config);
}
