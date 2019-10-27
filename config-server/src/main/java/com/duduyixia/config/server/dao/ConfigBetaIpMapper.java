package com.duduyixia.config.server.dao;

import com.duduyixia.config.server.bean.ConfigBetaClient;
import com.duduyixia.config.server.bean.ConfigData;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * created by WangTao on 2019-10-23
 */
@Repository
public interface ConfigBetaIpMapper {

    public List<ConfigBetaClient> getBetaIps(ConfigData configData);

    /*
    CREATE TABLE `config_beta_client` (
            `id` int(11) NOT NULL AUTO_INCREMENT,
	`config_id` int(11) NOT NULL,
	`ip` varchar(32) NOT NULL,
	`exist` tinyint NOT NULL,
            `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
            `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `idx_client`(`config_id`,`exist`,`ip`)
            ) ENGINE=InnoDB
    DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci;
    */

}
