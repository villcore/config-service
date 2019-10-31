# config-service
A simple config center service.


### SQL
#### 1. config_data
```
CREATE TABLE `config_data` (
                               `id` int(11) NOT NULL AUTO_INCREMENT,
                               `namespace` varchar(32) NOT NULL,
                               `env` varchar(32) NOT NULL,
                               `app` varchar(32) NOT NULL,
                               `config_group` varchar(32) NOT NULL,
                               `config` varchar(32) NOT NULL,
                               `config_value` text NOT NULL,
                               `md5` varchar(64) NOT NULL,
                               `mark_deleted` tinyint(4) NOT NULL DEFAULT 0,
                               `beta` tinyint(4) NOT NULL DEFAULT 0,
                               `beta_config_value` text NULL,
                               `beta_md5` varchar(64) NULL,
                               `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `idx_config`(`namespace`,`env`,`app`,`config_group`,`config`)
) ENGINE=InnoDB
  DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci;
```

#### 2. config_beta_client
```
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
```

### Bug 

### Test
1. 多server实例
2. 多client，beta测试
