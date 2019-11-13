package com.dliyun.plugin.filemanager;

import com.dliyun.platform.common.plugin.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/4/24 17:17
 */
@Plugin(key = "filemanager", name = "文件管理", faicon = "server")
public class Config implements RegisterPlugin {
    @Override
    public List<PluginModuleInfo> registerModule() {
        List<PluginModuleInfo> listPluginModules = new ArrayList<>();

        listPluginModules.add(buildDemoModule1Info());

        return listPluginModules;
    }

    @Override
    public List<UpgradeSqlInfo> getListUpgradeSqls() {
        return null;
    }

    private PluginModuleInfo buildDemoModule1Info() {
        PluginModuleInfo moduleInfo = new PluginModuleInfo("filemanager", "文件管理", "globe");

        //注册菜单
        //带权限控制的菜单
        moduleInfo.add(new PluginMenu("filemanager", "文件管理", "/filemanager/index.htm", "file.manager.find"));

        //注册权限
        moduleInfo.add(new PluginAuthority("file.manager.find", "文件查询"));

        //注册配置项
        moduleInfo.add(new PluginConfig("file.root", "文件根目录", PluginConfig.ConfigType.string));

        return moduleInfo;
    }
}
