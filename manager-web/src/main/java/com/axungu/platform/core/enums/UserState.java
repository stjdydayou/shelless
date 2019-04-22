package com.axungu.platform.core.enums;

/**
 * @author jtoms
 */

public enum UserState {

    normal(1, "正常"), disable(0, "禁用");

    private int code;
    private String text;

    UserState(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public static UserState valueOf(int code) {
        UserState[] values = UserState.values();
        for (UserState type : values) {
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
