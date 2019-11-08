package com.villcore.config.server.util;

import java.util.Objects;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {

    private final AtomicInteger seq;
    private final String namePrefix;
    private final boolean daemon;

    public NamedThreadFactory(String namePrefix, boolean daemon) {
        Objects.requireNonNull(namePrefix);

        this.seq = new AtomicInteger();
        this.namePrefix = namePrefix;
        this.daemon = daemon;
    }

    @Override
    public Thread newThread(Runnable target) {
        Thread thread = new Thread(target, namePrefix + "-" + seq.getAndIncrement());
        thread.setDaemon(daemon);
        return thread;
    }
}
