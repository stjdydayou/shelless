package com.dliyun.platform.core.model;

import java.io.Serializable;

/**
 * @author Administrator
 * @date 2017-6-7
 */
public class SystemOauthUserLoginAccount implements Serializable {

    private static final long serialVersionUID = -6476815660455154143L;

    private String id;

    private Long uid;

    private String loginAccount;

    private AccountType accountType;

    public static SystemOauthUserLoginAccount instance(String loginAccount, AccountType accountType) {
        SystemOauthUserLoginAccount account = new SystemOauthUserLoginAccount();
        account.setLoginAccount(loginAccount);
        account.setAccountType(accountType);
        return account;
    }

    public String getId() {
        if (id == null) {
            id = String.format("%s%010d", accountType.getCode(), uid);
        }
        return id;
    }

    public void setId(String id) {
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
}
