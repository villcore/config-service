package com.duduyixia.config.server.service;

import com.duduyixia.config.server.bean.ConfigKey;
import com.duduyixia.config.server.dto.ClientConfigInfo;
import com.duduyixia.config.server.dto.ConfigWatcherDTO;
import com.duduyixia.config.server.sync.ConfigSynchronizer;
import com.duduyixia.config.server.sync.RedisConfigSyncNotifier;
import com.google.gson.Gson;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Gson gson = new Gson();
    private JedisPool jedisPool;

    // TODO: use value annotation
    private String redisHost = "127.0.0.1";
    private int redisPort = 6379;

    private ConfigSynchronizer synchronizer;

    public RedisConfigWatcherManager(ConfigManager configManager) {
        super(configManager);
        initialize();
    }

    private void initialize() {
        jedisPool = buildJedisPool();
        this.synchronizer = new RedisConfigSyncNotifier(jedisPool);
    }

    private JedisPool buildJedisPool() {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(2);
        poolConfig.setMaxTotal(16);
        return new JedisPool(poolConfig, redisHost, redisPort);
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
        return gson.toJson(clientConfigInfo);
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
                return gson.fromJson(entry.getValue(), ClientConfigInfo.class);
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
