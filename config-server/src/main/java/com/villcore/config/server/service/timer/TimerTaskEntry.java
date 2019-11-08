package com.villcore.config.server.service.timer;

public class TimerTaskEntry implements Comparable<TimerTaskEntry> {

    private final TimerTask timerTask;
    private final long expirationMs;

    private volatile TimerTaskList list;
    private TimerTaskEntry next;
    private TimerTaskEntry prev;

    public TimerTaskEntry(TimerTask timerTask, long expirationMs) {
        this.timerTask = timerTask;
        this.expirationMs = expirationMs;

        if (timerTask != null) {
            this.timerTask.setTimerTaskEntry(this);
        }
    }

    public boolean cancelled() {
        return timerTask.getTimerTaskEntry() != this;
    }

    public void remove() {
        TimerTaskList currentList = list;
        while (currentList != null) {
            currentList.remove(this);
            currentList = list;
        }
    }

    public TimerTaskEntry getNext() {
        return next;
    }

    public void setNext(TimerTaskEntry next) {
        this.next = next;
    }

    public TimerTaskEntry getPrev() {
        return prev;
    }

    public void setPrev(TimerTaskEntry prev) {
        this.prev = prev;
    }

    public TimerTask getTimerTask() {
        return timerTask;
    }

    public TimerTaskList getTimerTaskList() {
        return list;
    }

    public void setTimerTaskList(TimerTaskList list) {
        this.list = list;
    }

    public synchronized boolean removeFromList(TimerTaskList timerTaskList) {
        if (getTimerTaskList() == timerTaskList) {
            getNext().setPrev(getPrev());
            getPrev().setNext(getNext());
            setNext(null);
            setPrev(null);
            setTimerTaskList(null);
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(TimerTaskEntry o) {
        return Long.compare(expirationMs, o.expirationMs);
    }

    public long getExpirationMs() {
        return expirationMs;
    }
}
