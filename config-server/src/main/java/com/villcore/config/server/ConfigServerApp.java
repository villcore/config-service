package com.villcore.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * created by WangTao on 2019-10-26
 */
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
},scanBasePackages = "com.villcore.config.server")
@EnableAsync
public class ConfigServerApp {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApp.class, args);
    }
}
