package com.duduyixia.config.server.controller;

import com.duduyixia.config.server.bean.ConfigKey;
import com.duduyixia.config.server.dto.ClientConfigInfo;
import com.duduyixia.config.server.dto.ConfigDataDTO;
import com.duduyixia.config.server.dto.ConfigWatcherDTO;
import com.duduyixia.config.server.service.ConfigAdminService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * created by WangTao on 2019-10-08
 */
@RestController
public class ConfigAdminController {

    @Resource
    private ConfigAdminService configAdminService;

    @RequestMapping("api/v1/config/admin/publish")
    @ResponseBody
    public Object publishConfig(ConfigKey configKey, String configValue) {
        if (configKey == null || configValue == null) {
            // TODO: error
        }

        return configAdminService.publishConfig(configKey, configValue);
    }

    @RequestMapping("api/v1/config/admin/publish_beta")
    public Object publishBetaConfig(ConfigKey configKey, boolean isBeta, List<String> clientIp) {

        // TODO:
        return null;
    }

    @RequestMapping("api/v1/config/admin/delete")
    public Object deleteConfig(ConfigKey configKey) {

        // TODO:
        return null;
    }

    @RequestMapping("api/v1/config/admin/list_client")
    public ConfigWatcherDTO listClient(@RequestBody ConfigKey configKey) {
        if (configKey == null) {
            return new ConfigWatcherDTO(null, Collections.emptyList());
        }
        return configAdminService.listConfigClient(configKey);
    }
}
