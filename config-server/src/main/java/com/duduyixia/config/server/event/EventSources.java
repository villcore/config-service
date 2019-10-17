package com.duduyixia.config.server.event;

import com.duduyixia.config.server.bean.ConfigKey;

/**
 * created by WangTao on 2019-10-17
 */
public class EventSources {

    public static EventSource<ConfigKey> configChangeEventSource() {
        return EventSourceHelper.getEventSoource("config_change_event");
    }

    public static EventSource<Long> currentTimeMillisEventSource() {
        return EventSourceHelper.getEventSoource("current_time_event");
    }

    public static void closeAll() {
        MapEventSourceRegistry.getInstance().closeAll();
    }
}
