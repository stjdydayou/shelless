package com.axungu.platform.core.enums;

/**
 * @author jtoms
 */

public enum AccountType {
    userName(0, "账号"), email(1, "邮箱"), mp(2, "手机号");

    private int code;
    private String text;

    AccountType(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public static AccountType valueOf(int code) {
        AccountType[] values = AccountType.values();
        for (AccountType type : values) {
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
