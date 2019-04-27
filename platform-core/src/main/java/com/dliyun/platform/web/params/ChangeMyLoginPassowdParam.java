package com.dliyun.platform.web.params;

import com.dliyun.platform.common.validation.StrongPassword;

import javax.validation.constraints.NotBlank;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2018/12/4 16:09
 */
public class ChangeMyLoginPassowdParam {

    @NotBlank(message = "请输入旧的登录密码")
    private String oldPassword;

    @NotBlank(message = "请输入新登录密码")
    @StrongPassword(message = "新登录密码必需同时包含字母与数字,长度不能小于6")
    private String newPassword;

    @NotBlank(message = "请输入确认登录密码")
    private String reNewPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getReNewPassword() {
        return reNewPassword;
    }

    public void setReNewPassword(String reNewPassword) {
        this.reNewPassword = reNewPassword;
    }
}
