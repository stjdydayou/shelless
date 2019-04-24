package com.dliyun.plugin.demo;

import com.dliyun.platform.common.plugin.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/4/24 17:17
 */
@Plugin(key = "demoPlugin", name = "测试插件", faicon = "server")
public class Config implements RegisterPlugin {
    @Override
    public List<PluginModuleInfo> registerModule() {
        List<PluginModuleInfo> listPluginModules = new ArrayList<>();

        listPluginModules.add(buildDemoModule1Info());
        listPluginModules.add(buildDemoModule2Info());

        return listPluginModules;
    }

    private PluginModuleInfo buildDemoModule1Info() {
        PluginModuleInfo moduleInfo = new PluginModuleInfo("demoModule1", "测试模块1", "globe");

        //注册菜单
        //带权限控制的菜单
        moduleInfo.add(new PluginMenu("demo-menu1", "测试菜单1", "/host/group/index.htm", "demo.menu1.find"));
        //只要登录就可以访问的菜单
        moduleInfo.add(new PluginMenu("demo-menu2", "测试菜单2", "/host/auth/index.htm"));

        //注册权限
        moduleInfo.add(new PluginAuthority("demo.menu1.find", "测试权限1"));


        //注册配置项
        moduleInfo.add(new PluginConfig("demo.config1", "测试配置项目1", PluginConfig.ConfigType.string));

        return moduleInfo;
    }

    private PluginModuleInfo buildDemoModule2Info() {
        PluginModuleInfo moduleInfo = new PluginModuleInfo("demoModule2", "测试模块2", "globe");

        //注册菜单
        //带权限控制的菜单
        moduleInfo.add(new PluginMenu("demo-menu21", "测试菜单21", "/host/group/index.htm", "demo.menu21.find"));
        //只要登录就可以访问的菜单
        moduleInfo.add(new PluginMenu("demo-menu22", "测试菜单22", "/host/auth/index.htm"));

        //注册权限
        moduleInfo.add(new PluginAuthority("demo.menu21.find", "测试权限21"));


        //注册配置项
        moduleInfo.add(new PluginConfig("demo.config21", "测试配置项目21", PluginConfig.ConfigType.string));

        return moduleInfo;
    }
}
