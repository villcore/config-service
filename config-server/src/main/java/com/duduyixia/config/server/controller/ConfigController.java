package com.duduyixia.config.server.controller;

import com.duduyixia.config.server.bean.ConfigKey;
import com.duduyixia.config.server.util.RequestUtils;
import org.springframework.web.bind.annotation.*;

/**
 * created by WangTao on 2019-09-29
 */
@RestController
public class ConfigController {

    @GetMapping("api/v1/config/get")
    public Object getConfig(@RequestParam("configKey") String configKey) {
        ConfigKey key = ConfigKey.formFlatKey(configKey);
        String clientIp = RequestUtils.getRemoteIp();

        // get form cache
        // check betaIp, beta value
    }

    @PostMapping("api/v1/config/watch")
    public Object watchConfig() {

        // check md5 version, if modifyed, return , else wait register to timingwheel
    }
}
