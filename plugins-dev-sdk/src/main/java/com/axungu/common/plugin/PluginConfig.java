package com.axungu.common.plugin;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/4/22 22:04
 */
public class PluginConfig {

    private String key;

    private String name;

    private ConfigType type;

    public PluginConfig(String key, String name, ConfigType type) {
        this.key = key;
        this.name = name;
        this.type = type;
    }

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

    public ConfigType getType() {
        return type;
    }

    public void setType(ConfigType type) {
        this.type = type;
    }

    public enum ConfigType {
        string(1, "文本"), number(2, "数字"), bool(3, "布尔"), bstring(4, "多行文本");

        private int code;
        private String text;

        ConfigType(int code, String text) {
            this.code = code;
            this.text = text;
        }

        public int getCode() {
            return code;
        }

        public String getText() {
            return text;
        }
    }

}
