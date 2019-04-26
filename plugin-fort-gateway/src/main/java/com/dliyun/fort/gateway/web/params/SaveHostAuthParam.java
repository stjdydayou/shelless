package com.dliyun.fort.gateway.web.params;

import com.dliyun.fort.gateway.core.model.HostAuth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2018/12/4 16:09
 */
public class SaveHostAuthParam {

    @NotBlank(message = "名称不能为空")
    private String name;

    @NotNull(message = "请选择认证方式")
    private HostAuth.AuthType authType;

    @NotBlank(message = "用户不能为空")
    private String userName;

    private String remark;


    private String password;


    private String publicKey;
    private String passphrase;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HostAuth.AuthType getAuthType() {
        return authType;
    }

    public void setAuthType(HostAuth.AuthType authType) {
        this.authType = authType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase;
    }
}
