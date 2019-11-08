package com.villcore.config.client.demo;

import com.villcore.config.client.ConfigException;
import com.villcore.config.client.ConfigService;
import com.villcore.config.client.ConfigServiceFactory;

/**
 * created by WangTao on 2019-09-28
 */
public class ConfigClientDemo {
    public static void main(String[] args) throws ConfigException, InterruptedException {
        // set property
        System.setProperty("config.client.namespace", "middleware");
        System.setProperty("config.client.failover", "true");

        // create config service
        ConfigService configService = ConfigServiceFactory.createConfigService();

        // listener
        configService.addListener("test", config -> {
            System.out.println("config -> " + config);
        });

        // get config
        for (int i = 0; i < 1000; i++) {
            System.out.println(configService.getConfig("test"));
            Thread.sleep(1000);
        }

        Thread.sleep(1000 * 1000L);
    }
}
