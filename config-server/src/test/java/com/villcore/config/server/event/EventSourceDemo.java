package com.villcore.config.server.event;

import java.util.concurrent.*;

/**
 * created by WangTao on 2019-10-17
 */
public class EventSourceDemo {

    public synchronized static void println(String str) {
        System.out.println(str);
    }

    public static void main(String[] args) throws InterruptedException {
        EventSource<Long> timeMillisEventSource = EventSources.currentTimeMillisEventSource();

        // subscribe use main thread
        timeMillisEventSource.subscribe(e -> {
            println(Thread.currentThread().getName() + " -> " + e);
        });

        // subscribe use executor
        timeMillisEventSource.subscribe(new ThreadPoolExecutor(1,
                1, 100, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(100), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            }
        }), e -> {
            try {
                Thread.sleep(10);
                println(Thread.currentThread().getName() + " -> " + e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        for (int i = 0; i < 1000000000; i++) {
            timeMillisEventSource.publish(System.currentTimeMillis());
        }

        //timeMillisEventSource.complete();
        Thread.sleep(10 * 1000L);

        for (int i = 0; i < 100; i++) {
            timeMillisEventSource.publish(System.currentTimeMillis());
        }

        Thread.sleep(10 * 1000L);
    }
}
