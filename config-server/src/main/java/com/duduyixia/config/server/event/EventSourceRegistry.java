package com.duduyixia.config.server.event;

public interface EventSourceRegistry {

    public <T> boolean registerEventSource(EventSource<T> eventSource);

    public <T> EventSource<T> getEventSource(String eventSourceName);
}
