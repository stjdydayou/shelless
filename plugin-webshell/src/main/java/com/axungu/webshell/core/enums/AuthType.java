package com.axungu.webshell.core.enums;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/3/21 20:23
 */
public enum AuthType {
    password(0, "用户名/密码"), sshkey(1, "SSH密钥");

    private int code;
    private String text;

    AuthType(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public static AuthType valueOf(int code) {
        AuthType[] values = AuthType.values();
        for (AuthType type : values) {
            if (type.code == code) {
                return type;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getText() {
        return text;
    }
}