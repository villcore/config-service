package com.villcore.config.server.spring;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"com.villcorre.config.server.dao.rds.mapper"}, sqlSessionFactoryRef = "sqlSessionFactoryRDS")
public class MybatisDSConfiguration {

    @Autowired
    @Qualifier("ds")
    private DataSource ds;

    @Bean
    public SqlSessionFactory sqlSessionFactoryRDS() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(ds);
        factoryBean.setConfigLocation(new ClassPathResource("mybatis/mybatis-config.xml"));
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplateRDS() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactoryRDS());
    }


}
