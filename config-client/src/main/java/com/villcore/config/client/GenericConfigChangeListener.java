package com.villcore.config.client;

/**
 * created by WangTao on 2019-09-16
 */
public interface GenericConfigChangeListener<T> extends Listener{

    /**
     * 配置更新回调方法，如果指定了指定线程，该方法在指定线程调用
     *
     * @param config 回调的配置值
     */
    void onChange(T config);
}
