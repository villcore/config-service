package com.duduyixia.config.client;

import java.util.concurrent.Executor;

/**
 * created by WangTao on 2019-09-16
 */
public interface GenericConfigChangeListener<T> extends Listener{

    Executor executor();

    ConfigConverter<T> convert();

    void onChange(T config);
}
