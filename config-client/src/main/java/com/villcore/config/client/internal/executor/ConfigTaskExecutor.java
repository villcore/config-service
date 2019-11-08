package com.villcore.config.client.internal.executor;

import com.villcore.config.client.ConfigException;
import com.villcore.config.client.internal.config.ConfigData;
import com.villcore.config.client.internal.config.ConfigKey;
import com.villcore.config.client.internal.http.ConfigHttpClient;
import com.villcore.config.client.internal.wrapper.DefaultConfigDataWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * created by WangTao on 2019-09-21
 */
public class ConfigTaskExecutor extends ThreadPoolExecutor {

    private static final Logger log = LoggerFactory.getLogger(ConfigTaskExecutor.class);

    private final BlockingQueue<DefaultConfigDataWrapper> pendingConfigDataWrapper = new LinkedBlockingQueue<>();
    private final Map<ConfigKey, DefaultConfigDataWrapper> configDataWrapperMap = new HashMap<>();
    private volatile boolean shutdown = false;
    private Future<?> configTaskFuture;

    private static final Runnable WAKEUP_TASK = () -> {};

    public ConfigTaskExecutor(int seq) {
        super(1, 1, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100),
                new NamedThreadFactory("config-task-executor-" + seq, true),
                new DiscardOldestPolicy() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
                        super.rejectedExecution(r, e);
                        log.warn("ConfigTaskExecutor reject task [{}]", r);
                    }
                });
    }

    public void addConfigWrapper(DefaultConfigDataWrapper configDataWrapper) {
        pendingConfigDataWrapper.add(configDataWrapper);

        synchronized (this) {
            if (configTaskFuture == null) {
                configTaskFuture = this.submit(new ConfigTask());
            }
        }
        execute(WAKEUP_TASK);
    }

    private final class ConfigTask implements Runnable {

        @Override
        public void run() {
            while (!shutdown && !Thread.currentThread().isInterrupted()) {
                long startTimeMs = System.currentTimeMillis();

                // handle worker task
                handleWorkerTask();

                // handle pending config
                handlePendingConfig();

                // handle changed config
                handleChangedConfig();

                long endTimeMs = System.currentTimeMillis();
                long elapsed = endTimeMs - startTimeMs;
                log.info("ConfigTask loop use time [{}] millis", elapsed);
            }
        }

        private void handleWorkerTask() {
            BlockingQueue<Runnable> workerTaskQueue = ConfigTaskExecutor.this.getQueue();
            try {
                Runnable runnable = workerTaskQueue.poll(100, TimeUnit.MILLISECONDS);
                if (runnable != null) {
                    doRunTask(runnable);
                    if (!workerTaskQueue.isEmpty()) {
                        ArrayList<Runnable> runnableArrayList = new ArrayList<>(workerTaskQueue.size());
                        workerTaskQueue.drainTo(runnableArrayList);
                        doRunTask(runnableArrayList.toArray(new Runnable[0]));
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.warn("ConfigTask poll interrupted", e);
            }
        }

        private void doRunTask(Runnable... runnable) {
            for (Runnable task : runnable) {
                if (task == null) {
                    continue;
                }

                try {
                    task.run();
                } catch (Throwable e) {
                    log.error("ConfigTask run error", e);
                }
            }
        }

        private void handlePendingConfig() {
            DefaultConfigDataWrapper configDataWrapper;
            while ((configDataWrapper = pendingConfigDataWrapper.peek()) != null) {
                ConfigData configData = configDataWrapper.getConfigDataSnapshot();
                try {
                    configData = configDataWrapper.getConfigData();
                } catch (ConfigException e) {
                    log.error("Get pending config [{}] failed", configData.getConfigKey(), e);
                } finally {
                    configDataWrapperMap.put(configData.getConfigKey(), configDataWrapper);
                    pendingConfigDataWrapper.remove(configDataWrapper);
                }
            }
        }

        @SuppressWarnings("unchecked")
        private void handleChangedConfig() {
            List<ConfigKey> changedConfigList = listenConfigChange(configDataWrapperMap);
            List<ConfigKey> expiredConfigList = getExpiredConfig(configDataWrapperMap);
            List<ConfigKey> fetchedConfigKeyList = mergeList(changedConfigList, expiredConfigList);
            batchedRefresh(fetchedConfigKeyList);
        }

        @SuppressWarnings("unchecked")
        private List<ConfigKey> mergeList(List<ConfigKey>... lists) {
            LinkedList<ConfigKey> mergedList = new LinkedList<>();
            Arrays.stream(lists).forEach(mergedList::addAll);
            return mergedList;
        }

        private List<ConfigKey> listenConfigChange(Map<ConfigKey, DefaultConfigDataWrapper> configDataWrapperMap) {
            try {
                final Map<ConfigKey, String> configMd5 = new HashMap<>(configDataWrapperMap.size());
                configDataWrapperMap.forEach((k, v) -> configMd5.put(k, v.getConfigDataSnapshot().getMd5()));
                return ConfigHttpClient.getInstance().listenConfigChange(configMd5);
            } catch (Exception e) {
                log.error("Listener [{}] config change failed", configDataWrapperMap.size(), e);
            }
            return Collections.emptyList();
        }

        private List<ConfigKey> getExpiredConfig(Map<ConfigKey, DefaultConfigDataWrapper> configDataWrapperMap) {
            return configDataWrapperMap.values().stream()
                    .filter(DefaultConfigDataWrapper::isExpired)
                    .map(config -> config.getConfigDataSnapshot().getConfigKey())
                    .collect(Collectors.toList());
        }

        private void batchedRefresh(List<ConfigKey> fetchedConfigKeyList) {
            configDataWrapperMap.forEach((configKey, configDataWrapper) -> {
                if (!fetchedConfigKeyList.contains(configKey)) {
                    return;
                }

                try {
                    ConfigData configData = configDataWrapper.getConfigDataSnapshot();
                    ConfigData newConfigData = configDataWrapper.refreshConfigData();
                    boolean isMarkDeleted = newConfigData.isMarkDeleted();
                    boolean isChanged = !Objects.equals(newConfigData.getMd5(), configData.getMd5());

                    configDataWrapper.tryUpdateConfig(newConfigData);
                    configDataWrapper.refreshConfigUpdateTime();
                    if (isMarkDeleted) {
                        configDataWrapper.configMarkDeleted(configData, newConfigData);
                    } else if (isChanged) {
                        configDataWrapper.configChanged(configData, newConfigData);
                    }
                } catch (Exception e) {
                    log.error("Refresh config [{}] error", configKey, e);
                }
            });
        }
    }

    private static class NamedThreadFactory implements ThreadFactory {

        private final AtomicInteger seq;
        private final String namePrefix;
        private final boolean daemon;

        NamedThreadFactory(String namePrefix, boolean daemon) {
            Objects.requireNonNull(namePrefix);

            this.seq = new AtomicInteger();
            this.namePrefix = namePrefix;
            this.daemon = daemon;
        }

        @Override
        public Thread newThread(Runnable target) {
            Thread thread = new Thread(target, namePrefix + "-" + seq.getAndIncrement());
            thread.setDaemon(daemon);
            return thread;
        }
    }
}
