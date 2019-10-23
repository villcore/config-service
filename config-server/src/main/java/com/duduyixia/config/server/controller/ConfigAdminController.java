package com.duduyixia.config.server.controller;

import com.duduyixia.config.server.bean.ConfigKey;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * created by WangTao on 2019-10-08
 */
@RestController
public class ConfigAdminController {

    @RequestMapping("api/v1/config/admin/publish")
    public Object publishConfig(ConfigKey configKey, String configValue) {


        // TODO:
        return null;
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
    public Object listClient(ConfigKey configKey) {

        // TODO:
        return null;
    }
}
