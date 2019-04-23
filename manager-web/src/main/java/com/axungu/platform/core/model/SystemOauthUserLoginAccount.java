package com.axungu.platform.core.model;

import com.axungu.platform.core.enums.AccountType;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-6-7.
 */
public class SystemOauthUserLoginAccount implements Serializable {

    private static final long serialVersionUID = -6476815660455154143L;

    private Long id;

    private Long uid;

    private String loginAccount;

    private AccountType accountType;

    public static SystemOauthUserLoginAccount instance(String loginAccount, AccountType accountType) {
        SystemOauthUserLoginAccount account = new SystemOauthUserLoginAccount();
        account.setLoginAccount(loginAccount);
        account.setAccountType(accountType);
        return account;
    }

    public Long getId() {
        if (id == null) {
            id = Long.parseLong(String.format("%s%010d", accountType.getCode(), uid));
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

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
}
