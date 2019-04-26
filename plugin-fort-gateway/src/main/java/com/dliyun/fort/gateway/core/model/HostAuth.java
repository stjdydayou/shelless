package com.dliyun.fort.gateway.core.model;

import java.util.Date;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/3/21 20:19
 */
public class HostAuth {

    private Long id;
    private String name;
    private AuthType authType;
    private String userName;
    private String authText;
    private String remark;
    private Date createdTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AuthType getAuthType() {
        return authType;
    }

    public void setAuthType(AuthType authType) {
        this.authType = authType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAuthText() {
        return authText;
    }

    public void setAuthText(String authText) {
        this.authText = authText;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }


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
}
