package com.dliyun.platform.web.params;

import com.dliyun.platform.common.plugin.PluginConfig;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/4/23 22:29
 */
public class SystemConfigParam {

    @NotBlank
    private String pluginKey;

    @NotBlank
    private String moduleKey;

    @NotBlank
    private String configKey;

    @NotNull
    private PluginConfig.ConfigType configType;

    private String dataValue;

    public String getPluginKey() {
        return pluginKey;
    }

    public void setPluginKey(String pluginKey) {
        this.pluginKey = pluginKey;
    }

    public String getModuleKey() {
        return moduleKey;
    }

    public void setModuleKey(String moduleKey) {
        this.moduleKey = moduleKey;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    public PluginConfig.ConfigType getConfigType() {
        return configType;
    }

    public void setConfigType(PluginConfig.ConfigType configType) {
        this.configType = configType;
    }
}
