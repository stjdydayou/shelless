package com.dliyun.platform.web.controller;


import com.dliyun.platform.common.DwzJSON;
import com.dliyun.platform.common.exception.ServiceException;
import com.dliyun.platform.common.oauth.Permission;
import com.dliyun.platform.PluginInfo;
import com.dliyun.platform.core.model.SystemOauthRoleInfo;
import com.dliyun.platform.core.service.SystemOauthRoleInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.List;

/**
 * Created by shenyj on 17-3-24.
 */
@Controller
@RequestMapping("/system/oauth/role")
public class SystemOauthRoleController {


    @Autowired
    private SystemOauthRoleInfoService systemOauthRoleInfoService;

    @Permission(pluginKey = "system", moduleKey = "oauth", authority = "role.find")
    @RequestMapping("/index.htm")
    public String role(ModelMap modelMap) {
        List<SystemOauthRoleInfo> listData = this.systemOauthRoleInfoService.findAll();
        modelMap.addAttribute("listData", listData);
        return "/system/oauth/role/index";
    }

    @Permission(pluginKey = "system", moduleKey = "oauth", authority = "role.add")
    @RequestMapping("/add.htm")
    public String roleAdd(ModelMap modelMap) {
        modelMap.addAttribute("oauthRoleInfo", new SystemOauthRoleInfo());
        return "/system/oauth/role/edit";
    }

    @Permission(pluginKey = "system", moduleKey = "oauth", authority = "role.edit")
    @RequestMapping("/edit.htm")
    public String roleEdit(ModelMap modelMap, Long id) {
        SystemOauthRoleInfo oauthRoleInfo = this.systemOauthRoleInfoService.findById(id);
        modelMap.addAttribute("oauthRoleInfo", oauthRoleInfo);
        return "/system/oauth/role/edit";
    }

    @Permission(pluginKey = "system", moduleKey = "oauth", authority = {"role.add", "role.edit"})
    @ResponseBody
    @RequestMapping("/save.ajax")
    public DwzJSON save(SystemOauthRoleInfo oauthRoleInfo) throws ServiceException {
        if (StringUtils.isBlank(oauthRoleInfo.getName())) {
            return DwzJSON.body(DwzJSON.StatusCode.error).setMessage("请输入角色名称");
        }
        this.systemOauthRoleInfoService.save(oauthRoleInfo);
        return DwzJSON.body(DwzJSON.StatusCode.success).setMessage("保存成功").setCloseCurrent(true).setTabid("system", "oauth", "role");
    }

    @Permission(pluginKey = "system", moduleKey = "oauth", authority = "role.delete")
    @ResponseBody
    @RequestMapping("/delete.ajax")
    public DwzJSON roleDelete(Long[] ids) {
        if (ids != null) {
            for (Long id : ids) {
                if (id.equals(1000L)) {
                    continue;
                }
                this.systemOauthRoleInfoService.deleteById(id);
            }
        }
        return DwzJSON.body(DwzJSON.StatusCode.success).setMessage("删除成功");
    }

    @Permission(pluginKey = "system", moduleKey = "oauth", authority = "role.authority")
    @RequestMapping("/authority.htm")
    public String roleSetResource(ModelMap modelMap, Long id) {
        SystemOauthRoleInfo oauthRoleInfo = this.systemOauthRoleInfoService.findById(id);
        modelMap.addAttribute("oauthRoleInfo", oauthRoleInfo);

        Collection<PluginInfo> listPlugins = PluginInfo.REGISTERED_PLUGINS.values();

        List<String> listAuthorities = this.systemOauthRoleInfoService.findAuthorities(id);
        modelMap.addAttribute("listAuthorities", listAuthorities);
        modelMap.addAttribute("listPlugins", listPlugins);

        return "/system/oauth/role/authority";
    }

    @Permission(pluginKey = "system", moduleKey = "oauth", authority = "role.authority")
    @ResponseBody
    @RequestMapping("/authority.ajax")
    public DwzJSON roleSetResource(Long roleId, String[] authorities) throws ServiceException {
        this.systemOauthRoleInfoService.setAuthorities(roleId, authorities);
        return DwzJSON.body(DwzJSON.StatusCode.success).setCloseCurrent(true).setMessage("保存成功");
    }

}
