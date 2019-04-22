package com.axungu.platform.core.enums;

/**
 * @author jtoms
 */

public enum Gender {
    secret(0, "保密"), male(1, "男"), female(2, "女");

    private int code;
    private String text;

    Gender(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public static Gender valueOf(int code) {
        Gender[] values = Gender.values();
        for (Gender type : values) {
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