package com.dliyun.platform;

import com.dliyun.platform.common.plugin.PluginModuleInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/4/15 10:07
 */
public class PluginInfo {

    public static SortedMap<String, PluginInfo> REGISTERED_PLUGINS = new TreeMap<>();

    private String key;
    private String faicon;
    private String name;
    private List<PluginModuleInfo> listModules = new ArrayList<>();


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getFaicon() {
        return faicon;
    }

    public void setFaicon(String faicon) {
        this.faicon = faicon;
    }

    public List<PluginModuleInfo> getListModules() {
        return listModules;
    }

    public PluginInfo addModule(PluginModuleInfo moduleInfo) {
        this.listModules.add(moduleInfo);
        return this;
    }

    public PluginInfo addModules(List<PluginModuleInfo> listModules) {
        this.listModules.addAll(listModules);
        return this;
    }
}
