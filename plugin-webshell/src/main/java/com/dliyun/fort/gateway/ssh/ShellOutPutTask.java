package com.dliyun.fort.gateway.ssh;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/3/21 14:18
 */
@Slf4j
public class ShellOutPutTask extends Thread {
    private final WebSocketSession session;
    private String charset;

    public ShellOutPutTask(WebSocketSession session, String charset) {
        this.session = session;
        this.charset = charset;
    }

    @Override
    public void run() {
        int bufferSize = 8192;
        byte[] buffer = new byte[8192];

        try {

            SSHClient client = SessionStorage.get(session.getId());

            while (this.session.isOpen()) {
                int length = client.getSession().getStdout().read(buffer, 0, bufferSize);
                if (length <= -1) {
                    break;
                }

                String charset = this.getCharset();
                String data = new String(buffer, 0, length, charset);

                MessageResponse response = MessageResponse.instance(MessageResponse.Code.xterm, data);
                this.session.sendMessage(new TextMessage(response.toString()));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        try {
            if (this.session.isOpen()) {
                this.session.close();
            }
        } catch (Exception ignored) {

        }
    }

    public synchronized String getCharset() {
        return this.charset;
    }

    public synchronized void setCharset(String charset) {
        this.charset = charset;
    }
}

