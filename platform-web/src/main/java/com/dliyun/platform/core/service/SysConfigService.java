package com.dliyun.platform.core.service;

import com.dliyun.platform.common.model.Money;

import java.math.BigDecimal;

/**
 * @author jtoms
 */
public interface SysConfigService {

    /**
     * 保存一个配置项
     *
     * @param pluginKey
     * @param moduleKey
     * @param configKey
     * @param dataValue
     */
    void insertOrUpdate(String pluginKey, String moduleKey, String configKey, String dataValue);

    /**
     * 获取一个配置项详情
     *
     * @param pluginKey
     * @param moduleKey
     * @param configKey
     * @return
     */
    String findDataValue(String pluginKey, String moduleKey, String configKey);

    /**
     * 获取配置文件的值
     *
     * @param pluginKey
     * @param moduleKey
     * @param configKey
     * @return
     */
    String getStringValue(String pluginKey, String moduleKey, String configKey);

    /**
     * 获取配置文件的值
     *
     * @param pluginKey
     * @param moduleKey
     * @param configKey
     * @return
     */
    boolean getBooleanValue(String pluginKey, String moduleKey, String configKey);

    /**
     * 获取配置文件的值
     *
     * @param pluginKey
     * @param moduleKey
     * @param configKey
     * @return
     */
    BigDecimal getBigDecimalValue(String pluginKey, String moduleKey, String configKey);

    /**
     * 获取配置文件的值
     *
     * @param pluginKey
     * @param moduleKey
     * @param configKey
     * @return
     */
    Money getMoneyValue(String pluginKey, String moduleKey, String configKey);
}
