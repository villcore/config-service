package com.villcore.config.server.notifier;

import com.villcore.config.server.bean.ConfigKey;
import com.villcore.config.server.event.EventSources;
import com.villcore.config.server.sync.ConfigSynchronizer;
import com.villcore.config.server.sync.RedisConfigSyncNotifier;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.JedisPool;

/**
 * created by WangTao on 2019-10-18
 */
public class RedisConfigSynchronizerTest {

    private ConfigSynchronizer configSyncNotifier;
    private JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);

    @Before
    public void before() {
    }

    @Test
    public void testListener() throws InterruptedException {
        this.configSyncNotifier = new RedisConfigSyncNotifier(jedisPool) {
            @Override
            public void onConfigChanged(ConfigKey configKey) {
                EventSources.getConfigChangeEventSource().publish(configKey);
            }
        };

        EventSources.getConfigChangeEventSource().subscribe(System.out::println);

        Thread.sleep(1000 * 1000L);
    }

    @Test
    public void testNotify() throws InterruptedException  {
        this.configSyncNotifier = new RedisConfigSyncNotifier(jedisPool) {};

        for (int i = 0; i < 1000000; i++) {
            ConfigKey configKey = ConfigKey.valueOf("duduyixia", "dev", "config-service", "default", "test");
            configSyncNotifier.notifyConfigChanged(configKey);
            Thread.sleep( 1000L);
        }
    }
}
