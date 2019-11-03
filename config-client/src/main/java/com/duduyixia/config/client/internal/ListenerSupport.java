package com.duduyixia.config.client.internal;

import com.duduyixia.config.client.ConfigConverter;
import com.duduyixia.config.client.Listener;

import java.util.concurrent.Executor;

public abstract class ListenerSupport implements Listener {

    private ListenerSupport() {}

    public Executor executor() {
        return null;
    }

    public <T> ConfigConverter<T> convert() {
        return null;
    }

    public Listener listener() {
        return null;
    }

    static <T> ListenerSupport create(Executor executor, ConfigConverter<T> configConverter, Listener listener) {
        return new ListenerSupport(){
            @Override
            public Executor executor() {
                return executor;
            }

            @Override
            public <T> ConfigConverter<T> convert() {
                return (ConfigConverter<T>) configConverter;
            }

            @Override
            public Listener listener() {
                return listener;
            }
        };
    }
}
