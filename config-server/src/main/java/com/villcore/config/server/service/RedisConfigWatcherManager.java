package com.villcore.config.server.service;

import com.villcore.config.server.bean.ConfigKey;
import com.villcore.config.server.dto.ClientConfigInfo;
import com.villcore.config.server.dto.ConfigWatcherDTO;
import com.villcore.config.server.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * created by WangTao on 2019-10-16
 */
@Component
public class RedisConfigWatcherManager extends ConfigWatcherManager {

    private static final Logger log = LoggerFactory.getLogger(RedisConfigWatcherManager.class);

    private static final String CONFIG_WATCHER_KEY_PREFIX = "config_watcher_";
    private JedisPool jedisPool;

    @Autowired
    public RedisConfigWatcherManager(ConfigManager configManager, JedisPool jedisPool) {
        super(configManager);
        this.jedisPool = jedisPool;
    }

    @Override
    public void reportClientConfig(ConfigKey configKey, String configMd5, String clientIp) {
        try (Jedis jedis = jedisPool.getResource()) {
            String configWatcherKey = CONFIG_WATCHER_KEY_PREFIX + ConfigKey.toFlatKey(configKey);
            String clientConfigInfoJson = getClientConfigInfoJson(clientIp, configMd5);
            jedis.hset(configWatcherKey, Collections.singletonMap(clientIp, clientConfigInfoJson));
            jedis.expire(configWatcherKey, 90);
        } catch (Exception e) {
            log.error("Report client config [{}], md5 [{}], clientIp [{}] error", configKey, configMd5, clientIp);
        }
    }

    private String getClientConfigInfoJson(String clientIp, String configMd5) {
        ClientConfigInfo clientConfigInfo = new ClientConfigInfo();
        clientConfigInfo.setClientIp(clientIp);
        clientConfigInfo.setMd5(configMd5);
        clientConfigInfo.setReportTimeMillis(System.currentTimeMillis());
        return JsonUtils.toJson(clientConfigInfo);
    }

    @Override
    public ConfigWatcherDTO getConfigClient(ConfigKey configKey) {
        try (Jedis jedis = jedisPool.getResource()) {
            String configWatcherKey = CONFIG_WATCHER_KEY_PREFIX + ConfigKey.toFlatKey(configKey);
            Map<String, String> allConfigClientInfoJson = jedis.hgetAll(configWatcherKey);
            return getConfigClient(configKey, allConfigClientInfoJson);
        } catch (Exception e) {
            log.error("Get client config [{}] error", configKey);
        }
        return null;
    }

    private ConfigWatcherDTO getConfigClient(ConfigKey configKey, Map<String, String> allConfigClientInfoJson) {
        List<ClientConfigInfo> clientConfigInfoList = allConfigClientInfoJson.entrySet().stream().map(entry -> {
            try {
                return JsonUtils.fromJson(entry.getValue(), ClientConfigInfo.class);
            } catch (Exception e) {
                log.error("Get client config [{}], clientIp [{}] error", configKey, entry.getKey());
            }
            return null;
        }).collect(Collectors.toList());

        ConfigWatcherDTO configWatcherDTO = new ConfigWatcherDTO();
        configWatcherDTO.setConfigKey(configKey);
        configWatcherDTO.setClientConfigInfoList(clientConfigInfoList);
        return configWatcherDTO;
    }
}
