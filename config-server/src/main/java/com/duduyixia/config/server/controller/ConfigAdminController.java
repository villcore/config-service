package com.duduyixia.config.server.controller;

import com.duduyixia.config.server.bean.ConfigKey;
import com.duduyixia.config.server.dto.ConfigWatcherDTO;
import com.duduyixia.config.server.service.ConfigAdminService;
import com.duduyixia.config.server.web.Response;
import org.apache.commons.lang3.StringUtils;
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
    public Response<Boolean> publishConfig(ConfigKey configKey, String configValue) {
        if (!validateConfig(configKey, configValue)) {
            return Response.fail("参数错误");
        }
        Boolean result = configAdminService.publishConfig(configKey, configValue);
        return Response.success(result);
    }

    @RequestMapping("api/v1/config/admin/update")
    @ResponseBody
    public Response<Boolean> updateConfig(Integer configId, String configValue, boolean beta, List<String> betaClientIp) {
        if (StringUtils.isBlank(configValue)) {
            return Response.fail("参数错误");
        }
        betaClientIp = betaClientIp == null ? Collections.emptyList() : betaClientIp;
        Boolean result = configAdminService.updateConfig(configId, beta, configValue, betaClientIp);
        return Response.success(result);
    }

    @RequestMapping("api/v1/config/admin/delete")
    public Response<Boolean> deleteConfig(ConfigKey configKey) {
        if (!validateConfigKey(configKey)) {
            return Response.fail("参数错误");
        }
        Boolean result = configAdminService.deleteConfig(configKey);
        return Response.success(result);
    }

    @RequestMapping("api/v1/config/admin/list_client")
    public ConfigWatcherDTO listClient(@RequestBody ConfigKey configKey) {
        if (configKey == null) {
            return new ConfigWatcherDTO(null, Collections.emptyList());
        }
        return configAdminService.listConfigClient(configKey);
    }

    private boolean validateConfig(ConfigKey configKey, String configValue) {
        if (configKey == null) {
            return false;
        }

        if (!validateConfigKey(configKey)) {
            return false;
        }
        return !StringUtils.isBlank(configValue);
    }

    private boolean validateConfigKey(ConfigKey configKey) {
        if (StringUtils.isBlank(configKey.getNamespace())
                || StringUtils.isBlank(configKey.getEnv())
                || StringUtils.isBlank(configKey.getApp())
                || StringUtils.isBlank(configKey.getConfig())) {
            return false;
        }
        return true;
    }
}
