package com.duduyixia.config.server.controller;

import com.duduyixia.config.common.http.Response;
import com.duduyixia.config.server.bean.ConfigKey;
import com.duduyixia.config.server.dto.ConfigDataDTO;
import com.duduyixia.config.server.service.ConfigService;
import com.duduyixia.config.server.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.Resource;
import java.util.Collections;
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

    @PostMapping("api/v1/config/watch")
    public DeferredResult<Response<List<ConfigKey>>> watchConfig(@RequestBody Map<ConfigKey, String> configMd5,
                                                                 @RequestHeader("timeout") long timeoutMs) {
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

        // watch empty config
        if (configMd5 == null || configMd5.isEmpty()) {
            result.setResult(Response.success(Collections.emptyList()));
        }

        // watch and wait
        Consumer<List<ConfigKey>> resultConsumer = (changedConfigKeys) -> result.setResult(Response.success(changedConfigKeys));
        configService.watchConfig(configMd5, clientIp, shrinkTimeoutMs, resultConsumer);
        return result;
    }

    @GetMapping("api/v1/config/get")
    public ConfigDataDTO getConfig(@RequestParam("configKey") String configKey) {
        ConfigKey key = ConfigKey.formFlatKey(configKey);
        String clientIp = RequestUtils.getRemoteIp();
        return configService.getConfig(key, clientIp);
    }
}
