package com.axungu.platform.web;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/3/30 21:00
 */
public class AjaxResult {
    private String code = Code.SUCCESS.getCode();

    private String message = Code.SUCCESS.getMessage();

    private Object data;

    public static AjaxResult instance(String message) {
        AjaxResult json = new AjaxResult();
        json.setCode(Code.MESSAGE_ERROR);
        json.setMessage(message);
        return json;
    }
    public static AjaxResult instance() {
        AjaxResult json = new AjaxResult();
        json.setCode(Code.SUCCESS);
        json.setMessage("请求成功");
        return json;
    }

    public static AjaxResult instance(Code code) {
        AjaxResult json = new AjaxResult();
        json.setCode(code);
        json.setMessage(code.getMessage());
        return json;
    }

    public static AjaxResult instance(Code code, String message) {
        AjaxResult json = new AjaxResult();
        json.setCode(code);
        json.setMessage(message);
        return json;
    }

    public String getMessage() {
        return message;
    }

    public AjaxResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public AjaxResult setData(Object data) {
        this.data = data;
        return this;
    }

    public String getCode() {
        return code;
    }

    public AjaxResult setCode(Code code) {
        this.code = code.getCode();
        return this;
    }

    public enum Code {
        SUCCESS("SUCCESS", "请求成功"),
        MESSAGE_ERROR("MESSAGE_ERROR", "操作失败，请稍后再试"),
        MESSAGE_SUCCESS("MESSAGE_SUCCESS", "操作成功"),
        SERVER_ERROR("SERVER_ERROR", "服务器忙，请稍后再试"),
        NO_FOUND("NO_FOUND", "您访问的资源不存在"),
        FORBIDDEN("FORBIDDEN", "您没的权限访问此资源"),
        NO_LOGIN("NO_LOGIN", "您还没有登录，请先登录"),
        NOT_SUFFICIENT_FUNDS("NOT_SUFFICIENT_FUNDS", "您账户全额不足，请先充值");

        private String code;
        private String message;

        Code(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}
