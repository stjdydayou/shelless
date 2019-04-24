package com.dliyun.platform.core.vo;

import com.dliyun.platform.core.model.SystemOauthUserBaseInfo;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/4/23 17:08
 */
public class SystemOauthUserInfoVO {

    private String loginAccount;

    private SystemOauthUserBaseInfo.UserState state;

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public SystemOauthUserBaseInfo.UserState getState() {
        return state;
    }

    public void setState(SystemOauthUserBaseInfo.UserState state) {
        this.state = state;
    }
}
