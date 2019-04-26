package com.dliyun.fort.gateway.ssh;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dliyun.fort.gateway.core.model.HostAuth;
import com.dliyun.fort.gateway.core.model.HostInfo;
import com.dliyun.fort.gateway.core.service.HostAuthService;
import com.dliyun.fort.gateway.core.service.HostGroupService;
import com.dliyun.fort.gateway.core.service.HostInfoService;
import com.dliyun.platform.common.oauth.OauthInfo;
import com.dliyun.platform.common.oauth.OauthService;
import com.dliyun.platform.common.service.SysConfigService;
import com.dliyun.platform.common.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.util.List;
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
    private OauthService oauthService;

    @Autowired
    private HostInfoService hostInfoService;

    @Autowired
    private HostAuthService hostAuthService;

    @Autowired
    private HostGroupService hostGroupService;

    @Autowired
    private SysConfigService sysConfigService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        Map<String, Object> attributes = session.getAttributes();

        String accessToken = attributes.get("accessToken").toString();
        String hostId = attributes.get("hostId").toString();
        String cols = attributes.get("cols").toString();
        String rows = attributes.get("rows").toString();

        session.sendMessage(new TextMessage(MessageResponse.instance(MessageResponse.Code.info, this.getBanner()).toString()));


        OauthInfo oauthInfo = this.oauthService.getOAuth(accessToken);
        if (oauthInfo == null) {
            session.sendMessage(new TextMessage(MessageResponse.instance(MessageResponse.Code.info, "您的登录信息已经失效，请重新登录").toString()));
            session.close();
            return;
        }

        HostInfo hostInfo = this.hostInfoService.findById(Long.parseLong(hostId));
        if (hostInfo == null) {
            session.sendMessage(new TextMessage(MessageResponse.instance(MessageResponse.Code.info, "您要登录的主机已经不存在").toString()));
            session.close();
            return;
        }

        List<Long> listGroupUsers = this.hostGroupService.findUserIds(hostInfo.getGroupId());
        if (listGroupUsers == null || !listGroupUsers.contains(oauthInfo.getId())) {
            session.sendMessage(new TextMessage(MessageResponse.instance(MessageResponse.Code.info, "您没有被授权管理此主机，请不要随意操作").toString()));
            session.close();
            return;
        }

        HostAuth hostAuth = hostAuthService.findById(hostInfo.getAuthId());
        if (hostAuth == null) {
            session.sendMessage(new TextMessage(MessageResponse.instance(MessageResponse.Code.info, "您要登录的主机密钥已经不存在").toString()));
            session.close();
            return;
        }

        String desKey = this.sysConfigService.getStringValue("fortGateway", "hostManager", "des_key");


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
        buffer.append("Copyright © 2019\r\n");
        buffer.append("\u001b[31m-----------------------------------------------------\r\n");
        buffer.append("\u001b[0m");
        return buffer;
    }

}
