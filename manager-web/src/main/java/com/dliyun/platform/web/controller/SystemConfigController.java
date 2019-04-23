package com.dliyun.platform.web.controller;


import com.dliyun.platform.PluginInfo;
import com.dliyun.platform.common.DwzJSON;
import com.dliyun.platform.common.oauth.Permission;
import com.dliyun.platform.core.service.SysConfigService;
import com.dliyun.platform.web.params.SystemConfigParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/system/config")
public class SystemConfigController {

    @Autowired
    private SysConfigService sysConfigService;

    @RequestMapping("/index.htm")
    @Permission(pluginKey = "system", moduleKey = "setting", authority = "config.find")
    public String index(ModelMap modelMap) {
        Collection<PluginInfo> listPlugins = PluginInfo.REGISTERED_PLUGINS.values();

        modelMap.addAttribute("listPlugins", listPlugins);
        return "/system/config/index";
    }

    @RequestMapping("/edit.htm")
    @Permission(pluginKey = "system", moduleKey = "setting", authority = "config.edit")
    public String edit(ModelMap modelMap, @Valid SystemConfigParam param) {

        String dataValue = this.sysConfigService.findDataValue(param.getPluginKey(), param.getModuleKey(), param.getConfigKey());
        param.setDataValue(dataValue);

        modelMap.addAttribute("config", param);
        return "/system/config/edit";
    }

    @ResponseBody
    @RequestMapping("/save.ajax")
    @Permission(pluginKey = "system", moduleKey = "setting", authority = "config.edit")
    public DwzJSON save(@Valid SystemConfigParam param) {

        if (StringUtils.isBlank(param.getDataValue())) {
            return DwzJSON.body(DwzJSON.StatusCode.error, "请输入配置项的值");
        }
        this.sysConfigService.insertOrUpdate(param.getPluginKey(), param.getModuleKey(), param.getConfigKey(), param.getDataValue());

        return DwzJSON.body(DwzJSON.StatusCode.success, "保存配置项成功").setCloseCurrent(true);
    }

}
