package com.duduyixia.config.client.demo;

import com.duduyixia.config.client.ConfigException;
import com.duduyixia.config.client.ConfigService;
import com.duduyixia.config.client.ConfigServiceFactory;

/**
 * created by WangTao on 2019-09-28
 */
public class ConfigClientDemo {
    public static void main(String[] args) throws ConfigException, InterruptedException {
        // set property
        System.setProperty("config.client.namespace", "duduyixia");
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
