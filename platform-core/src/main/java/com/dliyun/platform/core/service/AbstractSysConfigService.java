package com.dliyun.platform.core.service;

import com.dliyun.platform.common.model.Money;
import com.dliyun.platform.common.service.SysConfigService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.math.BigDecimal;

/**
 * @author jtoms
 */
public abstract class AbstractSysConfigService implements SysConfigService {

    /**
     * 保存一个配置项
     *
     * @param pluginKey
     * @param moduleKey
     * @param configKey
     * @param dataValue
     */
    @CacheEvict(value = {"system:config"}, key = "#p0+':'+#p1+':'+#p2")
    public abstract void insertOrUpdate(String pluginKey, String moduleKey, String configKey, String dataValue);

    /**
     * 获取一个配置项详情
     *
     * @param pluginKey
     * @param moduleKey
     * @param configKey
     * @return
     */
    @Cacheable(value = {"system:config"}, key = "#p0+':'+#p1+':'+#p2")
    public abstract String findDataValue(String pluginKey, String moduleKey, String configKey);

    @Override
    public String getStringValue(String pluginKey, String moduleKey, String configKey) {
        return this.findDataValue(pluginKey, moduleKey, configKey);
    }

    @Override
    public boolean getBooleanValue(String pluginKey, String moduleKey, String configKey) {
        String dataValue = this.getStringValue(pluginKey, moduleKey, configKey);
        return Boolean.valueOf(dataValue);
    }

    @Override
    public BigDecimal getBigDecimalValue(String pluginKey, String moduleKey, String configKey) {
        String dataValue = this.getStringValue(pluginKey, moduleKey, configKey);

        return dataValue == null ? null : new BigDecimal(dataValue);
    }

    @Override
    public Money getMoneyValue(String pluginKey, String moduleKey, String configKey) {
        String dataValue = this.getStringValue(pluginKey, moduleKey, configKey);
        return dataValue == null ? Money.ZERO : new Money(dataValue);
    }

}
