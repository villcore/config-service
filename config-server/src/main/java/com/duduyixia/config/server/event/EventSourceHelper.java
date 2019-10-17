package com.duduyixia.config.server.event;

public class EventSourceHelper {

    public static <T> void registerEventSource(String name) {
        EventSource<T> eventSource = new EventSourceImpl<>(name);
        MapEventSourceRegistry.getInstance().registerEventSource(eventSource);
    }

    public static <T> EventSource<T> getEventSoource(String eventSourceName) {
        EventSource<T> eventSource = MapEventSourceRegistry.getInstance().getEventSource(eventSourceName);
        if (eventSource == null) {
            registerEventSource(eventSourceName);
        }
        return MapEventSourceRegistry.getInstance().getEventSource(eventSourceName);
    }
}
