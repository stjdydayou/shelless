package com.axungu.common.plugin;

import java.util.List;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/4/15 00:55
 */
public class PluginModuleInfo {

    private String title;
    private String faicon;

    private List<PluginAuthority> listAuthorities;
    private List<PluginMenu> listMenus;
    private List<PluginConfig> listConfigs;

    public PluginModuleInfo() {
        super();
    }

    public PluginModuleInfo(String title, String faicon) {
        this.title = title;
        this.faicon = faicon;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public void setListAuthorities(List<PluginAuthority> listAuthorities) {
        this.listAuthorities = listAuthorities;
    }

    public List<PluginMenu> getListMenus() {
        return listMenus;
    }

    public void setListMenus(List<PluginMenu> listMenus) {
        this.listMenus = listMenus;
    }

    public List<PluginConfig> getListConfigs() {
        return listConfigs;
    }

    public void setListConfigs(List<PluginConfig> listConfigs) {
        this.listConfigs = listConfigs;
    }
}
