package com.dliyun.platform.core.model;

import java.io.Serializable;

/**
 * @author jtoms
 */
public class SystemOauthUserPassword implements Serializable {

    private static final long serialVersionUID = 5320982429919793484L;

    private Long id;

    private Long uid;

    private String passwd;

    private String salt;

    private UserPasswordType type;

    public SystemOauthUserPassword() {

    }

    public SystemOauthUserPassword(Long uid, String passwd, String salt, UserPasswordType type) {
        this.uid = uid;
        this.passwd = passwd;
        this.salt = salt;
        this.type = type;
    }

    public Long getId() {
        if (id == null) {
            id = Long.parseLong(String.format("%s%010d", type.getCode(), uid));
        }
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd == null ? null : passwd.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public UserPasswordType getType() {
        return type;
    }

    public void setType(UserPasswordType type) {
        this.type = type;
    }

    public enum UserPasswordType {
        login(1, "登录密码"), trade(2, "交易密码");

        private int code;
        private String text;

        UserPasswordType(int code, String text) {
            this.code = code;
            this.text = text;
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

        public int getCode() {
            return code;
        }

        public String getText() {
            return text;
        }
    }
}