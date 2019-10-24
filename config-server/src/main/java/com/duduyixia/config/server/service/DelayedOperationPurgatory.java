package com.duduyixia.config.server.service;

import com.duduyixia.config.server.service.timer.SystemTimer;
import com.duduyixia.config.server.service.timer.Time;
import com.duduyixia.config.server.service.timer.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * created by WangTao on 2019-10-23
 */
public final class DelayedOperationPurgatory<T extends DelayedOperation> {

    private static final Logger log = LoggerFactory.getLogger(DelayedOperationPurgatory.class);

    private String purgatoryName;
    private Timer timeoutTimer;
    private int purgeInterval = 10;
    private boolean reaperEnable = true;
    private boolean timerEnable = true;

    private final ConcurrentMap<Object, Watchers> watchersForKey;
    private final ReentrantReadWriteLock removeWatchersLock;
    private final ExpiredOperationReaper expirationReaper;
    private final AtomicInteger estimatedTotalOperations;

    public DelayedOperationPurgatory(String purgatoryName) {
        this(purgatoryName, new SystemTimer(purgatoryName, 1, 20, Time.SYSTEM.hiResClockMs()),
                1000, true, true);
    }

    public DelayedOperationPurgatory(String purgatoryName, Timer timeoutTimer, int purgeInterval, boolean reaperEnable,
                                     boolean timerEnable) {
        this.purgatoryName = purgatoryName;
        this.timeoutTimer = timeoutTimer;
        this.purgeInterval = purgeInterval;
        this.reaperEnable = reaperEnable;
        this.timerEnable = timerEnable;

        this.watchersForKey = new ConcurrentHashMap<>();
        this.removeWatchersLock = new ReentrantReadWriteLock();
        this.expirationReaper = new ExpiredOperationReaper(purgatoryName);
        this.estimatedTotalOperations = new AtomicInteger(0);
        if (reaperEnable) {
            this.expirationReaper.start();
        }
    }

    public boolean tryCompleteElseWatch(T operation, List<Object> watchKeys) {
        assert watchKeys != null && !watchKeys.isEmpty();

        boolean isCompleted = operation.tryComplete();
        if (isCompleted) {
            return true;
        }

        boolean watchCreated = false;
        for (Object watchKey : watchKeys) {
            if (operation.isCompleted()) {
                return false;
            }

            watchForOperation(watchKey, operation);
            if (!watchCreated) {
                watchCreated = true;
                estimatedTotalOperations.incrementAndGet();
            }
        }

        isCompleted = operation.maybeTryComplete();
        if (isCompleted) {
            return true;
        }

        if (!operation.isCompleted()) {
            if (timerEnable) {
                timeoutTimer.add(operation);
            }

            if (operation.isCompleted()) {
                operation.cancel();
            }
        }

        return false;
    }

    public int checkAndComplete(Object key) {
        Watchers watchers;
        Lock readLock = removeWatchersLock.readLock();
        readLock.lock();
        try {
            watchers = watchersForKey.get(key);
        } finally {
            readLock.unlock();
        }

        if (watchers == null) {
            return 0;
        } else {
            return watchers.tryCompleteWatched();
        }
    }

    public int watched() {
        int sum = 0;
        for (Watchers watchers : allWatchers()) {
            sum += watchers.countWatched();
        }
        return sum;
    }

    public int delayed() {
        return timeoutTimer.size();
    }

    public List<T> cancelForKey(Object key) {
        Watchers watchers;
        Lock writeLock = removeWatchersLock.writeLock();
        writeLock.lock();
        try {
            watchers = watchersForKey.remove(key);
        } finally {
            writeLock.unlock();
        }

        if (watchers != null) {
            return watchers.cancel();
        }
        return Collections.emptyList();
    }

    private Iterable<Watchers> allWatchers() {
        Lock readLock = removeWatchersLock.readLock();
        readLock.lock();
        try {
            return watchersForKey.values();
        } finally {
            readLock.unlock();
        }
    }

    private void watchForOperation(Object key, T operation) {
        Watchers watchers;
        Lock readLock = removeWatchersLock.writeLock();
        readLock.lock();
        try {
            watchers = watchersForKey.get(key);
            if (watchers == null) {
                synchronized (watchersForKey) {
                    watchers = watchersForKey.computeIfAbsent(key, Watchers::new);
                }
            }
            watchers.watch(operation);
        } finally {
            readLock.unlock();
        }
    }

    public void removeKeyIfEmpty(Object key, Watchers watchers) {
        Lock writeLock = removeWatchersLock.writeLock();
        writeLock.lock();
        try {
            if (watchersForKey.get(key) != watchers) {
                return;
            }

            if (watchers != null && watchers.isEmpty()) {
                watchersForKey.remove(key);
            }
        } finally {
            writeLock.unlock();
        }
    }

    public void shutdown() {
        if (reaperEnable) {
            expirationReaper.shutdown();
        }
        timeoutTimer.shutdown();
    }

    public void advancedClock(long timeoutMs) {
        timeoutTimer.advanceClock(timeoutMs);
//        if (estimatedTotalOperations.get() - delayed() > this.purgeInterval) {
//            estimatedTotalOperations.set(delayed());
//            for (Watchers watchers : allWatchers()) {
//                watchers.purgeCompleted();
//            }
//        }
    }

    private class Watchers {
        private final Object key;

        public Watchers(Object key) {
            this.key = key;
        }

        private final ConcurrentLinkedQueue<T> operations = new ConcurrentLinkedQueue<>();

        public int countWatched() {
            return operations.size();
        }

        public boolean isEmpty() {
            return operations.isEmpty();
        }

        public void watch(T operation) {
            operations.add(operation);
        }

        public int tryCompleteWatched() {
            int completed = 0;

            Iterator<T> iterator = operations.iterator();
            while (iterator.hasNext()) {
                T operation = iterator.next();
                if (operation.isCompleted()) {
                    iterator.remove();
                } else {
                    if (operation.maybeTryComplete()) {
                        iterator.remove();
                        completed++;
                    }
                }
            }

            if (operations.isEmpty()) {
                removeKeyIfEmpty(key, this);
            }
            return completed;
        }

        public List<T> cancel() {
            List<T> canceledOperations = new LinkedList<>();

            Iterator<T> iterator = operations.iterator();
            while (iterator.hasNext()) {
                T operation = iterator.next();
                iterator.remove();
                operation.cancel();
                canceledOperations.add(operation);
            }
            return canceledOperations;
        }

        public int purgeCompleted() {
            int purged = 0;
            Iterator<T> iterator = operations.iterator();
            while (iterator.hasNext()) {
                T operation = iterator.next();
                if (operation.isCompleted()) {
                    iterator.remove();
                    purged ++;
                }
            }

            if (operations.isEmpty()) {
                removeKeyIfEmpty(key, this);
            }
            return purged;
        }
    }

    private class ExpiredOperationReaper extends Thread {

        private AtomicBoolean isRunning = new AtomicBoolean(true);

        public ExpiredOperationReaper(String porgatoryName) {
            setName("ExpirationReaper-" + purgatoryName);
            setDaemon(false);
        }

        @Override
        public void run() {
            log.info("Starting");
            while (isRunning.get()) {
                advancedClock(200L);
                System.out.println("reaper");
            }
            log.info("Stopped");
        }

        public void shutdown() {
            isRunning.set(false);
        }
    }
}
