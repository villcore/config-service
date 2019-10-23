package com.duduyixia.config.server.service.timer;

import com.duduyixia.config.server.util.NamedThreadFactory;

import javax.annotation.concurrent.ThreadSafe;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;

/**
 * created by WangTao on 2019-10-22
 */
@ThreadSafe
public class SystemTimer implements Timer {

    private String executorName;
    private long tickMs = 1;
    private int wheelSize = 20;
    private long startMs = Time.SYSTEM.hiResClockMs();
    private ExecutorService taskExecutor;

    private final DelayQueue<TimerTaskList> delayQueue;
    private final AtomicInteger taskCounter;
    private final TimingWheel timingWheel;

    private ReentrantReadWriteLock readWriteLock;
    private ReentrantReadWriteLock.ReadLock readLock;
    private ReentrantReadWriteLock.WriteLock writeLock;

    public SystemTimer(String executorName, long tickMs, int wheelSize, long startMs) {
        this.executorName = executorName;
        this.tickMs = tickMs;
        this.wheelSize = wheelSize;
        this.startMs = startMs;
        this.taskExecutor = Executors.newFixedThreadPool(1, new NamedThreadFactory(executorName, false));

        this.delayQueue = new DelayQueue<>();
        this.taskCounter = new AtomicInteger();
        this.timingWheel = new TimingWheel(tickMs, wheelSize, startMs, taskCounter, delayQueue);

        this.readWriteLock = new ReentrantReadWriteLock();
        this.readLock = readWriteLock.readLock();
        this.writeLock = readWriteLock.writeLock();
    }

    @Override
    public void add(TimerTask timerTask) {
        readLock.lock();
        try {
            addTimerTaskEntry(new TimerTaskEntry(timerTask, timerTask.delayMs + Time.SYSTEM.hiResClockMs()));
        } finally {
            readLock.unlock();
        }
    }

    private void addTimerTaskEntry(TimerTaskEntry timerTaskEntry) {
        if (!timingWheel.add(timerTaskEntry)) {
            if (!timerTaskEntry.cancelled()) {
                taskExecutor.submit(timerTaskEntry.getTimerTask());
            }
        }
    }

    private final Consumer<TimerTaskEntry> reinsert = this::addTimerTaskEntry;

    @Override
    public boolean advanceClock(long timeoutMs) {
        try {
            TimerTaskList bucket = delayQueue.poll(timeoutMs, TimeUnit.MILLISECONDS);
            if (bucket != null) {
                writeLock.lock();
                try {
                    while (bucket != null) {
                        timingWheel.advanceClock(timeoutMs);
                        bucket.flush(reinsert);
                        bucket = delayQueue.poll();
                    }
                } finally {
                    writeLock.unlock();
                }
            } else {
                return false;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public int size() {
        return taskCounter.get();
    }

    @Override
    public void shutdown() {
        taskExecutor.shutdown();
    }
}
