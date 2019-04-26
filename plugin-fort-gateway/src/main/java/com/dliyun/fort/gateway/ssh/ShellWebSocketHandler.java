package com.dliyun.fort.gateway.ssh;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dliyun.fort.gateway.core.model.HostAuth;
import com.dliyun.fort.gateway.core.model.HostInfo;
import com.dliyun.platform.common.service.SysConfigService;
import com.dliyun.platform.common.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.util.Map;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2018/12/6 00:25
 */
@Slf4j
@Component
public class ShellWebSocketHandler implements WebSocketHandler {


    @Autowired
    private SysConfigService sysConfigService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        Map<String, Object> attributes = session.getAttributes();

        HostInfo hostInfo = (HostInfo) attributes.get("hostInfo");
        HostAuth hostAuth = (HostAuth) attributes.get("hostAuth");
        String cols = attributes.get("cols").toString();
        String rows = attributes.get("rows").toString();

        String desKey = this.sysConfigService.getStringValue("fortGateway", "hostManager", "des_key");


        session.sendMessage(new TextMessage(MessageResponse.instance(MessageResponse.Code.info, this.getBanner()).toString()));
        SSHClient client = null;

        if (hostAuth.getAuthType().equals(HostAuth.AuthType.password)) {
            log.debug("使用用户名密码登录");
            String loginPasswd = CryptoDESUtil.decode(desKey, hostAuth.getAuthText());

            client = SSHClient.connect(hostInfo.getHostAddress(), hostInfo.getPortNumber(),
                    hostAuth.getUserName(), loginPasswd, Integer.parseInt(cols), Integer.parseInt(rows));
        }
        if (hostAuth.getAuthType().equals(HostAuth.AuthType.sshkey)) {
            log.debug("使用sshkey登录");
            String authJson = CryptoDESUtil.decode(desKey, hostAuth.getAuthText());

            JSONObject jsonObject = JSON.parseObject(authJson);
            String privateKey = jsonObject.getString("privateKey");
            String passphrase = jsonObject.getString("passphrase");

            client = SSHClient.connectWithSSHKey(hostInfo.getHostAddress(), hostInfo.getPortNumber(),
                    hostAuth.getUserName(), privateKey, passphrase, Integer.parseInt(cols), Integer.parseInt(rows));
        }

        if (client == null) {
            session.sendMessage(new TextMessage(MessageResponse.instance(MessageResponse.Code.info, "连接服务器失败！！").toString()));
            session.close();
            return;
        }
        SessionStorage.add(session.getId(), client);

        ShellOutPutTask task = new ShellOutPutTask(session, "UTF-8");

        task.start();

    }


    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

        String payload = message.getPayload().toString();
        log.debug(payload);
        MessageReceive receive = JSONObject.parseObject(payload, MessageReceive.class);

        if (MessageReceive.Cmd.sh.equals(receive.getCmd())) {
            SSHClient client = SessionStorage.get(session.getId());

            if (client != null) {
                client.write(receive.getData());
            }
        }

        if (MessageReceive.Cmd.heartbeat.equals(receive.getCmd())) {
            MessageResponse response = MessageResponse.instance(MessageResponse.Code.heartbeat, DateUtil.current().getTime());
            session.sendMessage(new TextMessage(response.toString()));
        }

        if (MessageReceive.Cmd.resize.equals(receive.getCmd())) {
//            SSHClient client = SessionStorage.get(session.getId());
//            if (client != null) {
//                JSONObject jsonObject = JSON.parseObject(receive.getData());
//                client.setWindow(jsonObject.getInteger("cols"), jsonObject.getInteger("rows"));
//            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable throwable) throws Exception {
        log.info("handleTransportError");
        SSHClient client = SessionStorage.get(session.getId());
        if (client != null) {
            client.close();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("afterConnectionClosed");
        SSHClient client = SessionStorage.get(session.getId());
        if (client != null) {
            client.close();
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }


    private StringBuilder getBanner() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("\u001b[32mWelcome to WebSSH.\r\n");
        buffer.append("Copyright © 2019 http://www.dliyun.com.\r\n");
        buffer.append("\u001b[31m-----------------------------------------------------\r\n");
        buffer.append("\u001b[0m");
        return buffer;
    }

}
