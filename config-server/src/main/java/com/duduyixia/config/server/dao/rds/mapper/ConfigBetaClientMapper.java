package com.duduyixia.config.server.dao.rds.mapper;

import com.duduyixia.config.server.bean.ConfigBetaClient;
import com.duduyixia.config.server.bean.ConfigBetaClientExample;
import java.util.List;

import com.duduyixia.config.server.bean.ConfigData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigBetaClientMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_beta_client
     *
     * @mbggenerated Sun Oct 27 20:15:34 CST 2019
     */
    int countByExample(ConfigBetaClientExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_beta_client
     *
     * @mbggenerated Sun Oct 27 20:15:34 CST 2019
     */
    int deleteByExample(ConfigBetaClientExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_beta_client
     *
     * @mbggenerated Sun Oct 27 20:15:34 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_beta_client
     *
     * @mbggenerated Sun Oct 27 20:15:34 CST 2019
     */
    int insert(ConfigBetaClient record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_beta_client
     *
     * @mbggenerated Sun Oct 27 20:15:34 CST 2019
     */
    int insertSelective(ConfigBetaClient record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_beta_client
     *
     * @mbggenerated Sun Oct 27 20:15:34 CST 2019
     */
    List<ConfigBetaClient> selectByExample(ConfigBetaClientExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_beta_client
     *
     * @mbggenerated Sun Oct 27 20:15:34 CST 2019
     */
    ConfigBetaClient selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_beta_client
     *
     * @mbggenerated Sun Oct 27 20:15:34 CST 2019
     */
    int updateByExampleSelective(@Param("record") ConfigBetaClient record, @Param("example") ConfigBetaClientExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_beta_client
     *
     * @mbggenerated Sun Oct 27 20:15:34 CST 2019
     */
    int updateByExample(@Param("record") ConfigBetaClient record, @Param("example") ConfigBetaClientExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_beta_client
     *
     * @mbggenerated Sun Oct 27 20:15:34 CST 2019
     */
    int updateByPrimaryKeySelective(ConfigBetaClient record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_beta_client
     *
     * @mbggenerated Sun Oct 27 20:15:34 CST 2019
     */
    int updateByPrimaryKey(ConfigBetaClient record);

    default List<ConfigBetaClient> getBetaIps(ConfigData configData) {
        ConfigBetaClientExample example = new ConfigBetaClientExample();
        example.createCriteria()
                .andConfigIdEqualTo(configData.getId())
                .andExistEqualTo(true);
        return selectByExample(example);
    }
}