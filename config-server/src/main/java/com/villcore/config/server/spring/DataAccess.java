package com.villcore.config.server.spring;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DataAccess {
    @Bean(name = "ds")

    @ConfigurationProperties(prefix = "spring.datasource.druid.rds")
    public DataSource dataSourceMars() {
        return DruidDataSourceBuilder.create().build();
    }
}
