package com.duduyixia.config.server.service;

import com.duduyixia.config.server.bean.ConfigKey;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * created by WangTao on 2019-10-15
 */
@Component
public class ClientWatcherManager {

    /**
     * 维护configKey与watchar client的关系，这个关系是内存维护（redis维护），不需要持久化
     */
    public void reportClientConfig(Map<ConfigKey, String> configMd5, String clientIp) {
        // key: config  value: clientIp, md5
        configMd5.forEach((k, v) -> {
        });
    }

    public List<Tuple<String, String>> getConfigClient(ConfigKey configKey) {

    }
}
