package com.duduyixia.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * created by WangTao on 2019-10-26
 */
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
},scanBasePackages = "com.duduyixia")
public class ConfigServerApp {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApp.class, args);
    }
}
