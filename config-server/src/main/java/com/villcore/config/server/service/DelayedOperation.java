package com.villcore.config.server.service;

import com.villcore.config.server.service.timer.TimerTask;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * created by WangTao on 2019-10-22
 */
public abstract class DelayedOperation extends TimerTask  {

    private final Lock lock;

    private final AtomicBoolean completed;

    public DelayedOperation(long delayMs, Lock lock) {
        this.delayMs = Math.toIntExact(delayMs);
        if (lock == null) {
            this.lock = new ReentrantLock();
        } else {
            this.lock = lock;
        }

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
