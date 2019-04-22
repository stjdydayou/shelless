package com.axungu.platform.core.model;

import com.axungu.platform.core.enums.UserPasswordType;

import java.io.Serializable;

public class UserPassword implements Serializable {

    private static final long serialVersionUID = 5320982429919793484L;

    private Long id;

    private Long uid;

    private String passwd;

    private String salt;

    private UserPasswordType type;

    public UserPassword() {

    }

    public UserPassword(Long uid, String passwd, String salt, UserPasswordType type) {
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
}