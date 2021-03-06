package com.villcore.config.server.event;

import com.villcore.config.server.bean.ConfigKey;

/**
 * created by WangTao on 2019-10-17
 */
public class EventSources {

    public static EventSource<ConfigKey> getConfigChangeEventSource() {
        return EventSourceHelper.getEventSoource("config_change_event");
    }

    public static EventSource<ConfigKey> getConfigPublishEventSource() {
        return EventSourceHelper.getEventSoource("config_publish_event");
    }

    public static EventSource<Long> currentTimeMillisEventSource() {
        return EventSourceHelper.getEventSoource("current_time_event");
    }

    public static void closeAll() {
        MapEventSourceRegistry.getInstance().closeAll();
    }
}
