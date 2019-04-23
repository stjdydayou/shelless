package com.dliyun.platform.common;

public class DwzJSON {
    private StatusCode statusCode = StatusCode.success;

    private String message;

    private String tabid = "";

    private boolean closeCurrent = false;

    private String forward = "";

    private String forwardConfirm = "";

    private boolean reloadNavtab = true;

    public static DwzJSON body(StatusCode statusCode) {
        DwzJSON json = new DwzJSON();
        json.setStatusCode(statusCode);
        return json;
    }

    public static DwzJSON body(String message) {
        DwzJSON json = new DwzJSON();
        json.setMessage(message);
        return json;
    }

    public static DwzJSON body(StatusCode statusCode, String message) {
        DwzJSON json = new DwzJSON();
        json.setStatusCode(statusCode);
        json.setMessage(message);
        return json;
    }

    public static DwzJSON body(StatusCode statusCode, String message, boolean closeCurrent) {
        DwzJSON json = new DwzJSON();
        json.setStatusCode(statusCode);
        json.setMessage(message);
        json.setCloseCurrent(closeCurrent);
        return json;
    }

    public Integer getStatusCode() {
        return statusCode.getValue();
    }

    public DwzJSON setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public DwzJSON setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getTabid() {
        return tabid;
    }

    public DwzJSON setTabid(String pluginKey, String moduleKey, String menuKey) {
        this.tabid = String.format("%s_%s_%s", pluginKey, moduleKey, menuKey);
        return this;
    }

    public boolean isCloseCurrent() {
        return closeCurrent;
    }

    public DwzJSON setCloseCurrent(boolean closeCurrent) {
        this.closeCurrent = closeCurrent;
        return this;
    }

    public String getForward() {
        return forward;
    }

    public DwzJSON setForward(String forward) {
        this.forward = forward;
        return this;
    }

    public String getForwardConfirm() {
        return forwardConfirm;
    }

    public DwzJSON setForwardConfirm(String forwardConfirm) {
        this.forwardConfirm = forwardConfirm;
        return this;
    }

    public boolean isReloadNavtab() {
        return reloadNavtab;
    }

    public DwzJSON setReloadNavtab(boolean reloadNavtab) {
        this.reloadNavtab = reloadNavtab;
        return this;
    }

    public enum StatusCode {
        error {
            @Override
            public Integer getValue() {
                return 300;
            }
        },
        success {
            @Override
            public Integer getValue() {
                return 200;
            }
        },
        forward {
            @Override
            public Integer getValue() {
                return 301;
            }
        };

        public abstract Integer getValue();
    }

}
