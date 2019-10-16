package com.dliyun.platform;

import com.dliyun.platform.common.plugin.*;

import java.util.ArrayList;
import java.util.List;

@Plugin(key = "system", name = "系统设置", faicon = "cogs")
public class CoreConfig implements RegisterPlugin {
    @Override
    public List<PluginModuleInfo> registerModule() {

        List<PluginModuleInfo> listPluginModules = new ArrayList<>();

        /*--------用户管理模块----------*/
        PluginModuleInfo userModuleInfo = new PluginModuleInfo("oauth", "用户管理", "users");

        //注册菜单
        userModuleInfo.add(new PluginMenu("user", "用户管理", "/system/oauth/user/index.htm", "user.find"));
        userModuleInfo.add(new PluginMenu("role", "角色管理", "/system/oauth/role/index.htm", "role.find"));

        //注册权限
        userModuleInfo.add(new PluginAuthority("user.find", "查询用户"));
        userModuleInfo.add(new PluginAuthority("user.add", "添加用户"));
        userModuleInfo.add(new PluginAuthority("user.enable", "启用用户"));
        userModuleInfo.add(new PluginAuthority("user.disable", "禁用用户"));
        userModuleInfo.add(new PluginAuthority("user.resetPassword", "重置用户密码"));
        userModuleInfo.add(new PluginAuthority("user.roles", "设置用户角色"));
        userModuleInfo.add(new PluginAuthority("role.find", "查询角色"));
        userModuleInfo.add(new PluginAuthority("role.add", "添加角色"));
        userModuleInfo.add(new PluginAuthority("role.edit", "编辑角色"));
        userModuleInfo.add(new PluginAuthority("role.delete", "删除角色"));
        userModuleInfo.add(new PluginAuthority("role.authority", "设置角色权限"));

        //注册配置
        userModuleInfo.add(new PluginConfig("default_password", "系统用户默认登录密码（用于添加用户与重置用户登录密码）", PluginConfig.ConfigType.string));

        listPluginModules.add(userModuleInfo);


        /*--------系统设置模块----------*/
        PluginModuleInfo settingModuleInfo = new PluginModuleInfo("setting", "系统设置", "cog");

        //注册菜单
        settingModuleInfo.add(new PluginMenu("config", "参数配置", "/system/config/index.htm", "config.find"));

        //注册权限
        settingModuleInfo.add(new PluginAuthority("config.find", "查询系统参数"));
        settingModuleInfo.add(new PluginAuthority("config.edit", "编辑系统参数"));

        //注册配置
        settingModuleInfo.add(new PluginConfig("title", "平台名称", PluginConfig.ConfigType.string));
        settingModuleInfo.add(new PluginConfig("keywords", "平台关键词", PluginConfig.ConfigType.string));
        settingModuleInfo.add(new PluginConfig("description", "平台关描述", PluginConfig.ConfigType.bstring));
        settingModuleInfo.add(new PluginConfig("logo_url", "Logo地址", PluginConfig.ConfigType.string));

        listPluginModules.add(settingModuleInfo);

        return listPluginModules;

    }

    @Override
    public List<UpgradeSqlInfo> getListUpgradeSqls() {
        List<UpgradeSqlInfo> listUpgradeSqls = new ArrayList<>();
        listUpgradeSqls.add(new UpgradeSqlInfo("init"));
        return listUpgradeSqls;
    }
}
