package com.duduyixia.config.server.event;

import com.duduyixia.config.server.bean.ConfigKey;

/**
 * created by WangTao on 2019-10-17
 */
public class EventSources {

    // TODO: 增加通知全局server端的事件
    public static EventSource<ConfigKey> getConfigChangeEventSource() {
        return EventSourceHelper.getEventSoource("config_change_event");
    }

    public static EventSource<Long> currentTimeMillisEventSource() {
        return EventSourceHelper.getEventSoource("current_time_event");
    }

    public static void closeAll() {
        MapEventSourceRegistry.getInstance().closeAll();
    }
}
