package com.dliyun.platform.core.service.impl;

import com.dliyun.platform.core.mappers.SysConfigMapper;
import com.dliyun.platform.core.service.AbstractSysConfigService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/4/23 23:19
 */
@Service
public class SysConfigServiceImpl extends AbstractSysConfigService {

    @Autowired
    private SysConfigMapper sysConfigMapper;

    @Override
    public void insertOrUpdate(String pluginKey, String moduleKey, String configKey, String dataValue) {
        String id = this.buildId(pluginKey, moduleKey, configKey);
        this.sysConfigMapper.insertOrUpdate(id, dataValue);
    }

    @Override
    public String findDataValue(String pluginKey, String moduleKey, String configKey) {
        String id = this.buildId(pluginKey, moduleKey, configKey);
        return this.sysConfigMapper.findDataValueById(id);
    }


    private String buildId(String pluginKey, String moduleKey, String configKey) {
        return DigestUtils.md5Hex(String.format("%s@%s@%s", pluginKey, moduleKey, configKey));
    }
}
