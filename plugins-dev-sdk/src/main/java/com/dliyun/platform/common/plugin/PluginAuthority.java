package com.dliyun.platform.common.plugin;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/4/15 10:40
 */
public class PluginAuthority {
    private String key;
    private String name;

    public PluginAuthority(String key, String name) {
        this.key = key;
        this.name = name;
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
