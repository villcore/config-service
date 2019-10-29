package com.duduyixia.config.server.dao.rds.mapper;

import com.duduyixia.config.server.bean.ConfigData;
import com.duduyixia.config.server.bean.ConfigDataExample;
import java.util.List;

import com.duduyixia.config.server.bean.ConfigKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.util.CollectionUtils;

public interface ConfigDataMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_data
     *
     * @mbggenerated Tue Oct 29 14:12:34 CST 2019
     */
    int countByExample(ConfigDataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_data
     *
     * @mbggenerated Tue Oct 29 14:12:34 CST 2019
     */
    int deleteByExample(ConfigDataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_data
     *
     * @mbggenerated Tue Oct 29 14:12:34 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_data
     *
     * @mbggenerated Tue Oct 29 14:12:34 CST 2019
     */
    int insert(ConfigData record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_data
     *
     * @mbggenerated Tue Oct 29 14:12:34 CST 2019
     */
    int insertSelective(ConfigData record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_data
     *
     * @mbggenerated Tue Oct 29 14:12:34 CST 2019
     */
    List<ConfigData> selectByExampleWithBLOBs(ConfigDataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_data
     *
     * @mbggenerated Tue Oct 29 14:12:34 CST 2019
     */
    List<ConfigData> selectByExample(ConfigDataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_data
     *
     * @mbggenerated Tue Oct 29 14:12:34 CST 2019
     */
    ConfigData selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_data
     *
     * @mbggenerated Tue Oct 29 14:12:34 CST 2019
     */
    int updateByExampleSelective(@Param("record") ConfigData record, @Param("example") ConfigDataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_data
     *
     * @mbggenerated Tue Oct 29 14:12:34 CST 2019
     */
    int updateByExampleWithBLOBs(@Param("record") ConfigData record, @Param("example") ConfigDataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_data
     *
     * @mbggenerated Tue Oct 29 14:12:34 CST 2019
     */
    int updateByExample(@Param("record") ConfigData record, @Param("example") ConfigDataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_data
     *
     * @mbggenerated Tue Oct 29 14:12:34 CST 2019
     */
    int updateByPrimaryKeySelective(ConfigData record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_data
     *
     * @mbggenerated Tue Oct 29 14:12:34 CST 2019
     */
    int updateByPrimaryKeyWithBLOBs(ConfigData record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_data
     *
     * @mbggenerated Tue Oct 29 14:12:34 CST 2019
     */
    int updateByPrimaryKey(ConfigData record);

    default ConfigData getConfig(ConfigKey configKey) {
        ConfigDataExample example = new ConfigDataExample();
        example.createCriteria()
                .andNamespaceEqualTo(configKey.getNamespace())
                .andEnvEqualTo(configKey.getEnv())
                .andAppEqualTo(configKey.getApp())
                .andConfigGroupEqualTo(configKey.getGroup())
                .andConfigEqualTo(configKey.getConfig());

        List<ConfigData> configDataList = selectByExampleWithBLOBs(example);
        if (CollectionUtils.isEmpty(configDataList)) {
            return null;
        } else {
            return configDataList.get(0);
        }
    }
}