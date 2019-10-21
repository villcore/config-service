package com.duduyixia.config.server.service;

import com.duduyixia.config.server.bean.ConfigKey;
import com.duduyixia.config.server.util.NamedThreadFactory;

import java.util.Objects;
import java.util.concurrent.*;

public class ScheduledConfigWatcherPurgatoryImpl implements ConfigWatcherPurgatory {

    private final ScheduledExecutorService scheduledExecutorService;
    private final ConcurrentMap<ConfigKey, WatcherTask> watcherTaskMap;

    public ScheduledConfigWatcherPurgatoryImpl() {
        scheduledExecutorService = Executors.newScheduledThreadPool(4,
                new NamedThreadFactory("scheduled-config-watcher-purgatory-reaper", true));
        watcherTaskMap = new ConcurrentHashMap<>();
    }

    @Override
    public void watchConfig(ConfigKey configKey, Runnable callback, long timeoutMs) {

    }

    @Override
    public void tryComplete(ConfigKey configKey) {

    }

    private static class WatcherTask {
        ConfigKey configKey;
        Runnable action;
        Future<Void> future;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WatcherTask that = (WatcherTask) o;
            return Objects.equals(configKey, that.configKey) &&
                    Objects.equals(action, that.action) &&
                    Objects.equals(future, that.future);
        }

        @Override
        public int hashCode() {
            return Objects.hash(configKey, action, future);
        }
    }
}
