package com.duduyixia.config.server.dao;

import com.duduyixia.config.server.bean.ConfigData;
import com.duduyixia.config.server.bean.ConfigKey;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * created by WangTao on 2019-10-23
 */
@Repository
public interface ConfigDataMapper {

    @Select("select * from config_data where namespace = #{configKey.namespace} and env = #{configKey.env}" +
            " and app = #{configKey.app} and `group` = #{configKey.group} and config = #{configKey.config} limit 1")
    ConfigData getConfig(@Param("configKey") ConfigKey configKey);

    @Select("select * from config_data where id = #{configId}")
    ConfigData getConfig(@Param("configId") Integer configId);

    @Insert("INSERT INTO config_data (namespace, env, app, `group`, config, value, md5) VALUES (" +
            "#{configData.namespace}, #{configData.env}, #{configData.app}, #{configData.group}," +
            " #{configData.config}, #{configData.value}, #{configData.md5})")
    int insertConfig(@Param("configData") ConfigData configData);

    @Update("UPDATE config_data SET `value` = #{configData.value}, md5 = #{configData.md5} WHERE id = #{configData.id}")
    boolean updateConfig(@Param("configData") ConfigData configData);

    @Update("")
    boolean setConfigBeta(@Param("configData") ConfigData configData);

    /*
    CREATE TABLE `config_data` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`namespace` varchar(32) NOT NULL,
	`env` varchar(32) NOT NULL,
	`app` varchar(32) NOT NULL,
	`group` varchar(32) NOT NULL,
	`config` varchar(32) NOT NULL,
	`value` text NOT NULL,
	`md5` varchar(64) NOT NULL,
	`mark_deleted` tinyint(4) NOT NULL DEFAULT 0,
	`beta` tinyint(4) NOT NULL DEFAULT 0,
	`beta_value` text NULL,
	`beta_md5` varchar(64) NULL,
	`update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`),
	UNIQUE KEY `idx_config`(`namespace`,`env`,`app`,`group`,`config`)
) ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci;
    */
}
