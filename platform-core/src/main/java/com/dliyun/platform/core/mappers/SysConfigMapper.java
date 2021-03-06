package com.dliyun.platform.core.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/4/23 23:20
 */
public interface SysConfigMapper {
    /**
     * 插入或者是更新配置项目的值
     *
     * @param id
     * @param dataValue
     */
    void insertOrUpdate(@Param("id") String id, @Param("dataValue") String dataValue);

    /**
     * 查询一个配置项的值
     *
     * @param id
     * @return
     */
    String findDataValueById(@Param("id") String id);
}
