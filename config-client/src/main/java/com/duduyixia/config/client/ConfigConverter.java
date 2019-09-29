package com.duduyixia.config.client;

/**
 * created by WangTao on 2019-09-19
 */
public interface ConfigConverter<T> {

    T convert(String configValue);
}
