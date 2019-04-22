package com.axungu.common.plugin;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/4/22 22:04
 */
public class PluginConfig {

    private String key;

    private String desc;

    private ConfigType type;

    public PluginConfig(String key, String desc, ConfigType type) {
        this.key = key;
        this.desc = desc;
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ConfigType getType() {
        return type;
    }

    public void setType(ConfigType type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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
