package com.axungu.common.plugin;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/4/15 00:55
 */
public class PluginMenu {

    private String key;
    private String url;
    private String name;

    public PluginMenu(String key, String name, String url) {
        this.key = key;
        this.name = name;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
