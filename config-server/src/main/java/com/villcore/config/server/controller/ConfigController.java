package com.villcore.config.server.controller;

import com.villcore.config.common.http.Response;
import com.villcore.config.server.bean.ConfigKey;
import com.villcore.config.server.bean.WatchConfig;
import com.villcore.config.server.dto.ConfigDataDTO;
import com.villcore.config.server.service.ConfigService;
import com.villcore.config.server.util.RequestUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * created by WangTao on 2019-09-29
 */
@RestController
public class ConfigController {

    private static final Logger log = LoggerFactory.getLogger(ConfigController.class);

    @Resource
    private ConfigService configService;

    @Resource
    private ObjectMapper objectMapper;

    @PostMapping("api/v1/config/watch")
    public DeferredResult<Response<List<ConfigKey>>> watchConfig(@RequestParam("configMd5") String configMd5Json,
                                                                 @RequestHeader("timeout") long timeoutMs) throws Exception {
        if (timeoutMs < 5000L) {
            throw new IllegalArgumentException("timeoutMs must large than 5000 ms");
        }

        String clientIp = RequestUtils.getRemoteIp();
        long shrinkTimeoutMs = timeoutMs - 1000L;

        // define a deferred result
        final DeferredResult<Response<List<ConfigKey>>> result = new DeferredResult<>(shrinkTimeoutMs, Response.success(Collections.emptyList()));
        result.onCompletion(() -> log.info("DeferredResult #{}:{} completed ", clientIp, result.hashCode()));
        result.onError(throwable -> log.error("DeferredResult #{}:{} error ", clientIp, result.hashCode(), throwable));
        result.onTimeout(() -> log.warn("DeferredResult #{}:{} timeout ", clientIp, result.hashCode()));

        List<WatchConfig> watchConfig = objectMapper.readValue(configMd5Json, new TypeReference<List<WatchConfig>>(){});
        Map<String, String> configMd5 = new HashMap<>(watchConfig.size());
        for (WatchConfig config : watchConfig) {
            configMd5.put(config.getConfigFlatKey(), config.getMd5());
        }
        // watch empty config
        if (configMd5 == null || configMd5.isEmpty()) {
            result.setResult(Response.success(Collections.emptyList()));
            return result;
        }

        Map<ConfigKey, String> configKeyWithMd5 = new HashMap<>(configMd5.size());
        configMd5.forEach((k, v) -> {
            configKeyWithMd5.put(ConfigKey.formFlatKey(k), v);
        });

        // watch and wait
        Consumer<List<ConfigKey>> resultConsumer = (changedConfigKeys) -> result.setResult(Response.success(changedConfigKeys));
        configService.watchConfig(configKeyWithMd5, clientIp, shrinkTimeoutMs, resultConsumer);
        return result;
    }

    @GetMapping("api/v1/config/get")
    public ConfigDataDTO getConfig(@RequestParam("configKey") String configKey) {
        ConfigKey key = ConfigKey.formFlatKey(configKey);
        String clientIp = RequestUtils.getRemoteIp();
        return configService.getConfig(key, clientIp);
    }
}
