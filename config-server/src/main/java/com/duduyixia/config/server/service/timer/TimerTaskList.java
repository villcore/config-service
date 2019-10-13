package com.duduyixia.config.server.service.timer;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public class TimerTaskList implements Comparable<TimerTaskList> {

    private final TimerTaskEntry root = new TimerTaskEntry(null, -1);

    private final AtomicInteger taskCounter;
    private final AtomicLong expiration;

    public TimerTaskList(AtomicInteger taskCounter) {
        this.taskCounter = taskCounter;
        this.expiration = new AtomicLong(-1L);

        root.setNext(root);
        root.setPrev(root);
    }

    public boolean setExpiration(long expirationMs) {
        return expiration.getAndSet(expirationMs) != expirationMs;
    }

    public long getExpiration() {
        return expiration.get();
    }

    public synchronized void foreach(Consumer<TimerTask> consumer) {
        TimerTaskEntry entry = root.getNext();
        while (Objects.equals(entry, root)) {
            TimerTaskEntry nextEntry = entry.getNext();

            if (!entry.cancelled()) {
                consumer.accept(entry.getTimerTask());
            }
            entry = nextEntry;
        }
    }

    public void add(TimerTaskEntry timerTaskEntry) {
        boolean done = false;
        while (!done) {
            timerTaskEntry.remove();

            synchronized (this) {
                if (timerTaskEntry.getTimerTaskList() != null) {
                    return;
                }

                TimerTaskEntry tail = root.getPrev();
                timerTaskEntry.setNext(root);
                timerTaskEntry.setPrev(tail);
                timerTaskEntry.setTimerTaskList(this);
                tail.setNext(timerTaskEntry);
                tail.setPrev(timerTaskEntry);
                taskCounter.incrementAndGet();
                done = true;
            }
        }
    }

    public void remove(TimerTaskEntry timerTaskEntry) {
        synchronized (this) {
            timerTaskEntry.removeFormList(this);
            taskCounter.decrementAndGet();
        }
    }

    public void flush(Consumer<TimerTaskEntry> consumer) {
        synchronized (this) {
            TimerTaskEntry head = root.getNext();
            while (head != root) {
                remove(head);
                consumer.accept(head);
                head = root.getNext();
            }
            expiration.set(-1L);
        }
    }

    public long getDelay(TimeUnit unit) {
        return unit.convert(Math.max(0, getExpiration() - Time.SYSTEM.hiResClockMs()), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(TimerTaskList o) {
        return Long.compare(getExpiration(), o.getExpiration());
    }
}
