package com.duduyixia.config.server.service;

import com.duduyixia.config.server.service.timer.TimerTask;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;

/**
 * created by WangTao on 2019-10-22
 */
public abstract class DelayedOperation extends TimerTask  {

    private final long delayMs;
    private final Lock lock;

    private final AtomicBoolean completed;

    public DelayedOperation(long delayMs, Lock lock) {
        this.delayMs = delayMs;
        this.lock = lock;

        this.completed = new AtomicBoolean(false);
    }

    public boolean forceComplete() {
        if (completed.compareAndSet(false, true)) {
            cancel();
            onComplete();
            return true;
        } else {
            return false;
        }
    }

    public boolean isCompleted() {
        return completed.get();
    }

    public abstract void onExpiration();

    public abstract void onComplete();


    public abstract boolean tryComplete();
    public boolean maybeTryComplete() {
        if (lock.tryLock()) {
            try {
                return tryComplete();
            } finally {
                lock.unlock();
            }
        } else {
            return false;
        }
    }

    @Override
    public void run() {
        if (forceComplete()) {
            onExpiration();
        }
    }
}
