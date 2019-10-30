package com.duduyixia.config.client.internal.executor;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * created by WangTao on 2019-09-21
 */
public class ConfigTaskExecutorGroup {

    private final ConfigTaskExecutor[] configTaskExecutors;
    private final ConfigTaskExecutorChooser configTaskExecutorChooser;

    public ConfigTaskExecutorGroup() {
        this(Math.min(Runtime.getRuntime().availableProcessors() * 2, 4));
    }

    public ConfigTaskExecutorGroup(int workerPoolSize) {
        this.configTaskExecutors = buildConfigTaskExecutorPool(workerPoolSize);
        this.configTaskExecutorChooser = new ConfigTaskExecutorChooser(configTaskExecutors);
    }

    private ConfigTaskExecutor[] buildConfigTaskExecutorPool(int workerPoolSize) {
        ConfigTaskExecutor[] configTaskExecutors = new ConfigTaskExecutor[workerPoolSize];
        for (int i = 0; i < configTaskExecutors.length; i++) {
            configTaskExecutors[i] = new ConfigTaskExecutor(i);
        }
        return configTaskExecutors;
    }

    public ConfigTaskExecutor next() {
        return configTaskExecutorChooser.choose();
    }

    private static class ConfigTaskExecutorChooser {

        private final ConfigTaskExecutor[] configTaskExecutors;
        private final AtomicInteger seq;

        public ConfigTaskExecutorChooser(ConfigTaskExecutor[] configTaskExecutors) {
            this.configTaskExecutors = configTaskExecutors;
            this.seq = new AtomicInteger();
        }

        public ConfigTaskExecutor choose() {
            int index = toPositive(seq.getAndIncrement()) % configTaskExecutors.length;
            return configTaskExecutors[index];
        }

        private int toPositive(int number) {
            return number & 0x7fffffff;
        }
    }
}
