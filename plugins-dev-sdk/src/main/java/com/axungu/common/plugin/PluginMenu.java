package com.axungu.common.plugin;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/4/15 00:55
 */
public class PluginMenu {

    private String tabid;
    private String url;
    private String title;

    public PluginMenu(String tabid, String title, String url) {
        this.tabid = tabid;
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTabid() {
        return tabid;
    }

    public void setTabid(String tabid) {
        this.tabid = tabid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
