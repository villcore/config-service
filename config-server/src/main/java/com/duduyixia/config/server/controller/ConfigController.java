package com.duduyixia.config.server.controller;

import com.duduyixia.config.server.bean.ConfigKey;
import com.duduyixia.config.server.dto.ConfigDataDTO;
import com.duduyixia.config.server.service.ConfigService;
import com.duduyixia.config.server.util.RequestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * created by WangTao on 2019-09-29
 */
@RestController
public class ConfigController {

    @Resource
    private ConfigService configService;

    @GetMapping("api/v1/config/get")
    public ConfigDataDTO getConfig(@RequestParam("configKey") String configKey) {
        ConfigKey key = ConfigKey.formFlatKey(configKey);
        String clientIp = RequestUtils.getRemoteIp();
        return configService.getConfig(key, clientIp);
    }

    @PostMapping("api/v1/config/watch")
    public Map<ConfigKey, ConfigDataDTO> watchConfig(@RequestParam("configMd5") Map<ConfigKey, String> configMd5, long timeoutMs) {
        // TODO: 异步化处理http
        String clientIp = RequestUtils.getRemoteIp();
        return configService.watchConfig(configMd5, clientIp, timeoutMs);
    }
}
