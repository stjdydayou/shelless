package com.dliyun.platform.common.plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/4/15 00:55
 */
public class PluginModuleInfo {

    private String key;
    private String name;
    private String faicon;

    private List<PluginAuthority> listAuthorities = new ArrayList<>();
    private List<PluginMenu> listMenus = new ArrayList<>();
    private List<PluginConfig> listConfigs = new ArrayList<>();

    public PluginModuleInfo() {
        super();
    }

    public PluginModuleInfo(String key, String name, String faicon) {
        this.key = key;
        this.name = name;
        this.faicon = faicon;
    }

    public String getFaicon() {
        return faicon;
    }

    public void setFaicon(String faicon) {
        this.faicon = faicon;
    }

    public List<PluginAuthority> getListAuthorities() {
        return listAuthorities;
    }

    public List<PluginMenu> getListMenus() {
        return listMenus;
    }

    public List<PluginConfig> getListConfigs() {
        return listConfigs;
    }

    public PluginModuleInfo add(PluginMenu menu) {
        this.listMenus.add(menu);
        return this;
    }

    public PluginModuleInfo add(PluginAuthority authority) {
        this.listAuthorities.add(authority);
        return this;
    }

    public PluginModuleInfo add(PluginConfig config) {
        this.listConfigs.add(config);
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
