package com.axungu.platform;

import com.axungu.common.plugin.PluginModuleInfo;

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
    private List<PluginModuleInfo> listModules;


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

    public void setListModules(List<PluginModuleInfo> listModules) {
        this.listModules = listModules;
    }
}
