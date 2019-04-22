package com.axungu.platform.web.controller;


import com.axungu.common.oauth.Permission;
import com.axungu.platform.PluginInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping("/system/config")
public class SystemConfigController {

    @RequestMapping("/index.htm")
    @Permission(pluginKey = "system", moduleKey = "setting", authority = "config.find")
    public String index(ModelMap modelMap) {
        Collection<PluginInfo> listPlugins = PluginInfo.REGISTERED_PLUGINS.values();

        modelMap.addAttribute("listPlugins", listPlugins);
        return "/system/config/index";
    }

    @RequestMapping("/edit.htm")
    @Permission(pluginKey = "system", moduleKey = "setting", authority = "config.edit")
    public String edit(ModelMap modelMap) {
        Collection<PluginInfo> listPlugins = PluginInfo.REGISTERED_PLUGINS.values();

        modelMap.addAttribute("listPlugins", listPlugins);
        return "/system/config/index";
    }

}
