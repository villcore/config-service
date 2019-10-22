package com.duduyixia.config.server.service.timer;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class TimingWheel {

    private final long tickMs;
    private final int wheelSize;
    private final long startMs;
    private final AtomicInteger taskCounter;
    private final DelayQueue<TimerTaskList> queue;

    private final long interval;
    private final TimerTaskList[] buckets;
    private long currentTime;

    private volatile TimingWheel overflowWheel;

    public TimingWheel(long tickMs, int wheelSize, long startMs, AtomicInteger taskCounter, DelayQueue<TimerTaskList> queue) {
        this.tickMs = tickMs;
        this.wheelSize = wheelSize;
        this.startMs = startMs;
        this.taskCounter = taskCounter;
        this.queue = queue;

        this.interval = tickMs * wheelSize;
        this.buckets = createBucket(wheelSize);
        this.currentTime = startMs - (startMs % tickMs);
        this.overflowWheel = null;
    }

    private TimerTaskList[] createBucket(int wheelSize) {
        TimerTaskList[] buckets = new TimerTaskList[wheelSize];
        for (int i = 0; i < wheelSize; i++) {
            buckets[i] = new TimerTaskList(taskCounter);
        }
        return buckets;
    }

    private void addOverflowWheel() {
        synchronized (this) {
            if (overflowWheel == null) {
                overflowWheel = new TimingWheel(interval, wheelSize, currentTime, taskCounter, queue);
            }
        }
    }

    public boolean add(TimerTaskEntry timerTaskEntry) {
        long expirationMs = timerTaskEntry.getExpirationMs();

        if (timerTaskEntry.cancelled()) {
            return false;
        } else  if (expirationMs < currentTime + tickMs) {
            return false;
        } else if (expirationMs < currentTime + interval) {
            int virtualId = (int) (expirationMs / tickMs);
            TimerTaskList bucket = buckets[virtualId % wheelSize];
            bucket.add(timerTaskEntry);

            if (bucket.setExpiration(virtualId * tickMs)) {
                queue.offer(bucket);
            }
            return true;
        } else {
            if (overflowWheel == null) {
                addOverflowWheel();
            }
            return overflowWheel.add(timerTaskEntry);
        }
    }

    public void advanceClock(long timeMs) {
        if (timeMs >= currentTime + timeMs) {
            currentTime = timeMs - (timeMs % tickMs);
        }

        if (overflowWheel != null) {
            overflowWheel.advanceClock(currentTime);
        }
    }
}
