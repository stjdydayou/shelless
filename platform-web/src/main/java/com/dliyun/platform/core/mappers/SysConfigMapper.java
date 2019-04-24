package com.dliyun.platform.core.mappers;

import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

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
    @CacheEvict(value = {"system:config"}, key = "#p0")
    void insertOrUpdate(@Param("id") String id, @Param("dataValue") String dataValue);

    /**
     * 查询一个配置项的值
     *
     * @param id
     * @return
     */
    @Cacheable(value = {"system:config"}, key = "#p0")
    String findDataValueById(@Param("id") String id);
}
