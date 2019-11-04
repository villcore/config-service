package com.duduyixia.config.server.sync;

import com.duduyixia.config.server.bean.ConfigKey;
import com.duduyixia.config.server.event.EventSources;
import com.duduyixia.config.server.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import java.util.Arrays;
import java.util.concurrent.*;

/**
 * created by WangTao on 2019-10-18
 */
public class RedisConfigSyncNotifier implements ConfigSynchronizer {

    private static final Logger log = LoggerFactory.getLogger(RedisConfigSyncNotifier.class);

    private final JedisPool jedisPool;
    private final ExecutorService executor;
    private volatile boolean closed;
    private final ConfigSyncNotifierPubSub pubSub;

    public RedisConfigSyncNotifier(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
        this.pubSub = new ConfigSyncNotifierPubSub();
        this.executor = newThreadExecutor();
        initialized();
    }

    private ExecutorService newThreadExecutor() {
        return Executors.newSingleThreadExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "redis-config-sync-notifier-thread");
                thread.setDaemon(true);
                return thread;
            }
        });
    }

    private void initialized() {
        this.closed = false;
        this.executor.execute(() -> {
            while (!closed) {
                try (Jedis jedis = jedisPool.getResource()) {
                    jedis.subscribe(pubSub, configChangedChannel());
                } catch (Exception e) {
                    log.error("Subscribe channel " + configChangedChannel() + " failed, will retry after 5 second", e);
                }

                try {
                    TimeUnit.SECONDS.sleep(5L);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        EventSources.getConfigPublishEventSource().subscribe(this::notifyConfigChanged);
    }

    @Override
    public void notifyConfigChanged(ConfigKey configKey) {
        log.info("Notify config change {} ", configKey);
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.publish(configChangedChannel(), JsonUtils.toJson(configKey));
            log.info("Notify config {} change ", configKey);
        } catch (Exception e) {
            log.error("Notify config changed error", e);
        }
    }

    private String configChangedChannel() {
        return "config_service_config_changed";
    }

    @Override
    public void onConfigChanged(ConfigKey configKey) {
        EventSources.getConfigChangeEventSource().publish(configKey);
    }

    public void close() {
        this.closed = true;
        pubSub.unsubscribe();
        executor.shutdownNow();
    }

    private class ConfigSyncNotifierPubSub extends JedisPubSub {

        @Override
        public void onMessage(String channel, String message) {
            log.info("OnMessage from channel {} message {}", channel, message);
            try {
                ConfigKey changedConfigKey = JsonUtils.formJson(message, ConfigKey.class);
                onConfigChanged(changedConfigKey);
            } catch (Exception e) {
                log.warn("Convert message json {} to ConfigKey failed", message, e);
            }
        }

        @Override
        public void onSubscribe(String channel, int subscribedChannels) {
            log.info("OnSubscribe channel {} ", channel);
        }

        @Override
        public void onUnsubscribe(String channel, int subscribedChannels) {
            log.info("OnUnsubscribe channel {} ", channel);
        }

        @Override
        public void unsubscribe() {
            log.info("OnUnsubscribe ");
        }

        @Override
        public void unsubscribe(String... channels) {
            log.info("OnUnsubscribe {}", Arrays.asList(channels));

        }

        @Override
        public void subscribe(String... channels) {
            log.info("Subscribe {}", Arrays.asList(channels));
        }
    }
}
