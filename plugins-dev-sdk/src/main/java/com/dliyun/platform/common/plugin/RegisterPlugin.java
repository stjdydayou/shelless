package com.dliyun.platform.common.plugin;

import java.util.List;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/4/15 01:00
 */
public interface RegisterPlugin {

    /**
     * 注册插件模块
     *
     * @return
     */
    List<PluginModuleInfo> registerModule();

    /**
     * 注册升级SQL
     *
     * @return
     */
    List<UpgradeSqlInfo> getListUpgradeSqls();
}
