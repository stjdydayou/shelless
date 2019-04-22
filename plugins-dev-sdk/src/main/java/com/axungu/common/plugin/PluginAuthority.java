package com.axungu.common.plugin;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/4/15 10:40
 */
public class PluginAuthority {
    private String id;
    private String name;

    public PluginAuthority(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
