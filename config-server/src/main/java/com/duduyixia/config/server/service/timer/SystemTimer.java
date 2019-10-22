package com.duduyixia.config.server.service.timer;

import com.duduyixia.config.server.util.NamedThreadFactory;

import javax.annotation.concurrent.ThreadSafe;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * created by WangTao on 2019-10-22
 */
@ThreadSafe
public class SystemTimer implements Timer {

    private String executorName;
    private long tickMs = 1;
    private int wheelSize = 20;
    private long startMs = Time.SYSTEM.hiResClockMs();
    private Executor taskExecutor;

    private final DelayQueue<TimerTaskList> delayQueue;
    private final AtomicInteger taskCounter;

    public SystemTimer(String executorName, long tickMs, int wheelSize, long startMs) {
        this.executorName = executorName;
        this.tickMs = tickMs;
        this.wheelSize = wheelSize;
        this.startMs = startMs;
        this.taskExecutor = Executors.newFixedThreadPool(1, new NamedThreadFactory(executorName, false));

        this.delayQueue = new DelayQueue<>();
        this.taskCounter = new AtomicInteger();
    }

    @Override
    public void add(TimerTask timerTask) {

    }

    @Override
    public boolean advanceClock(long timeoutMs) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void shutdown() {

    }
}
