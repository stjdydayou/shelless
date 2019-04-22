package com.axungu.platform.core.enums;

public enum UserPasswordType {
    login(1, "登录密码"), trade(2, "交易密码");

    UserPasswordType(int code, String text) {
        this.code = code;
        this.text = text;
    }

    private int code;

    private String text;

    public int getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    public static UserPasswordType valueOf(int code) {
        UserPasswordType[] values = UserPasswordType.values();
        for (UserPasswordType type : values) {
            if (type.code == code) {
                return type;
            }
        }
        return null;
    }
}
