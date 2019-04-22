package com.axungu.common.exception;

/**
 * @author jtoms
 */
public class NoLoginException extends Exception {

    private static final long serialVersionUID = 3934335121640650570L;

    private String loginUrl;

    public NoLoginException() {
    }

    public NoLoginException(String loginUrl) {
        super("No Login");
        this.loginUrl = loginUrl;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }
}
