package com.duduyixia.config.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by WangTao on 2019-10-08
 */
@RestController
public class ConfigAdminController {

    @RequestMapping("api/v1/config/admin/publish")
    public Object publishConfig() {

        // TODO:
        return null;
    }

    @RequestMapping("api/v1/config/admin/publish_beta")
    public Object publishBetaConfig() {

        // TODO:
        return null;
    }

    @RequestMapping("api/v1/config/admin/delete")
    public Object deleteConfig() {

        // TODO:
        return null;
    }

    @RequestMapping("api/v1/config/admin/list_client")
    public Object listClient() {

        // TODO:
        return null;
    }
}
