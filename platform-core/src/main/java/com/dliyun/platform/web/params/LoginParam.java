package com.dliyun.platform.web.params;

import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2018/12/4 16:09
 */
public class LoginParam {

    @NotBlank(message = "请输入您的登录手机号或邮箱")
    private String loginAccount;

    @NotBlank(message = "请输入登录密码")
    private String loginPassword;

    @NotBlank(message = "请输入验证码")
    @Size(min = 4, max = 4, message = "验证只能是4位")
    private String captcha;

    public String getCaptcha() {
        return StringUtils.isBlank(captcha) ? null : captcha.toLowerCase().trim();
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getLoginAccount() {
        return StringUtils.isBlank(loginAccount) ? null : loginAccount.toLowerCase().trim();
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }
}
