package com.duduyixia.config.client.demo;

import com.duduyixia.config.client.ConfigException;
import com.duduyixia.config.client.ConfigService;
import com.duduyixia.config.client.ConfigServiceFactory;

/**
 * created by WangTao on 2019-09-28
 */
public class ConfigClientDemo {
    public static void main(String[] args) throws ConfigException, InterruptedException {
        ConfigService configService = ConfigServiceFactory.createConfigService();
        configService.addListener("test", config -> {
            System.out.println("config -> " + config);
        });
        for (int i = 0; i < 100000000; i++) {
            //System.out.println(configService.getConfig("test"));
            Thread.sleep(10);
        }

        Thread.sleep(1000 * 1000L);
    }
}
