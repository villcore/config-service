package com.duduyixia.config.server.service.timer;

public abstract class TimerTask implements Runnable {

    private int delayMs;
    private TimerTaskEntry timerTaskEntry;

    public synchronized void cancel() {
        if (timerTaskEntry != null) {
            timerTaskEntry.remove();
            timerTaskEntry = null;
        }
    }

    public synchronized void setTimerTaskEntry(TimerTaskEntry entry) {
        if (timerTaskEntry != null && timerTaskEntry != entry) {
            timerTaskEntry.remove();
        }

        timerTaskEntry = entry;
    }

    public TimerTaskEntry getTimerTaskEntry() {
        return timerTaskEntry;
    }
}