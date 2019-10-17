package com.duduyixia.config.server.event;

import com.google.common.base.Preconditions;

import java.awt.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * created by WangTao on 2019-10-17
 */
public class MapEventSourceRegistry implements EventSourceRegistry {

    private static final MapEventSourceRegistry INSTANCE = new MapEventSourceRegistry();

    public static MapEventSourceRegistry getInstance() {
        return INSTANCE;
    }

    private ConcurrentMap<String, EventSource> eventSourceMap = new ConcurrentHashMap<>();

    private MapEventSourceRegistry() {}

    @SuppressWarnings("unchecked")
    @Override
    public <T> boolean registerEventSource(EventSource<T> eventSource) {
        Preconditions.checkState(eventSource != null, "eventSource require not null");

        String eventSourceName = eventSource.name();
        Preconditions.checkState(eventSourceName != null && !eventSourceName.isEmpty(),
                "eventSource name require not empty");

        return eventSourceMap.putIfAbsent(eventSourceName, eventSource) == null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> EventSource<T> getEventSource(String eventSourceName) {
        Preconditions.checkState(eventSourceName != null && !eventSourceName.isEmpty(),
                "eventSource name require not empty");

        return (EventSource<T>) eventSourceMap.get(eventSourceName);
    }

    public void closeAll() {
        eventSourceMap.forEach((k, v) -> {
            v.close();
        });
    }
}
