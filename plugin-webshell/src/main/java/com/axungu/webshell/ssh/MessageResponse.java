package com.axungu.webshell.ssh;

import com.alibaba.fastjson.JSON;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2018/12/7 23:36
 */
public class MessageResponse {

    private String code;

    private Object data;

    public static MessageResponse instance(Code code) {
        MessageResponse json = new MessageResponse();
        json.setCode(code);
        return json;
    }

    public static MessageResponse instance(Code code, Object data) {
        MessageResponse response = new MessageResponse();
        response.setCode(code);
        response.setData(data);
        return response;
    }

    public Object getData() {
        return data;
    }

    public MessageResponse setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public String getCode() {
        return code;
    }

    public MessageResponse setCode(Code code) {
        this.code = code.name();
        return this;
    }

    public enum Code {
        info("提示信息"),
        heartbeat("心跳"),
        xterm("控制台消息");

        private String desc;

        Code(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }
    }
}
