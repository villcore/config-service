package com.villcore.config.server.service.timer;

import javax.annotation.concurrent.ThreadSafe;
import java.util.Objects;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

@ThreadSafe
public class TimerTaskList implements Delayed {

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
                root.setPrev(timerTaskEntry);
                taskCounter.incrementAndGet();
                done = true;
            }
        }
    }

    public void remove(TimerTaskEntry timerTaskEntry) {
        synchronized (this) {
            if (timerTaskEntry.removeFromList(this)) {
                taskCounter.decrementAndGet();
            }
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

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(Math.max(0, getExpiration() - Time.SYSTEM.hiResClockMs()), TimeUnit.MILLISECONDS);
    }

    public int compareTo(TimerTaskList o) {
        return Long.compare(getExpiration(), o.getExpiration());
    }

    @Override
    public int compareTo(Delayed o) {
        return Long.compare(getExpiration(), ((TimerTaskList) o).getExpiration());
    }
}
