package com.dliyun.fort.gateway.ssh;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2018/12/7 23:36
 */
public class MessageReceive {

    private Cmd cmd;

    private String data;

    public Cmd getCmd() {
        return cmd;
    }

    public void setCmd(Cmd cmd) {
        this.cmd = cmd;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    public enum Cmd {
        resize("窗口改变大小"),
        heartbeat("心跳"),
        sh("命令");

        private String desc;

        Cmd(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }
    }

}
