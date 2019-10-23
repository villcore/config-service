package com.duduyixia.config.server.service.timer;

/**
 * created by WangTao on 2019-10-22
 */
public interface Timer {

    /**
     *
     * @param timerTask
     */
    public void add(TimerTask timerTask);

    /**
     *
     * @param timeoutMs
     *
     * @return
     */
    public boolean advanceClock(long timeoutMs);

    /**
     *
     * @return
     */
    public int size();

    /**
     *
     */
    public void shutdown();
}
