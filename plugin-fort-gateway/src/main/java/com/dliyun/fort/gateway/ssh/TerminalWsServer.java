package com.dliyun.fort.gateway.ssh;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dliyun.fort.gateway.core.model.HostAuth;
import com.dliyun.fort.gateway.core.model.HostInfo;
import com.dliyun.fort.gateway.core.service.HostAuthService;
import com.dliyun.fort.gateway.core.service.HostGroupService;
import com.dliyun.fort.gateway.core.service.HostInfoService;
import com.dliyun.platform.common.SpringBeanFactory;
import com.dliyun.platform.common.oauth.OauthInfo;
import com.dliyun.platform.common.oauth.OauthService;
import com.dliyun.platform.common.service.SysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@ServerEndpoint("/ws/terminal/{accessToken}/{hostId}/{cols}/{rows}")
public class TerminalWsServer {

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("accessToken") String accessToken, @PathParam("hostId") String hostId, @PathParam("cols") String cols, @PathParam("rows") String rows) throws Exception {
        log.info("accessToken>>>>>>>>>{}", accessToken);
        log.info("hostId>>>>>>>>>{}", hostId);
        log.info("cols>>>>>>>>>{}", cols);
        log.info("rows>>>>>>>>>{}", rows);

        OauthService oauthService = SpringBeanFactory.getApplicationContext().getBean(OauthService.class);
        HostInfoService hostInfoService = SpringBeanFactory.getApplicationContext().getBean(HostInfoService.class);
        HostAuthService hostAuthService = SpringBeanFactory.getApplicationContext().getBean(HostAuthService.class);
        HostGroupService hostGroupService = SpringBeanFactory.getApplicationContext().getBean(HostGroupService.class);
        SysConfigService sysConfigService = SpringBeanFactory.getApplicationContext().getBean(SysConfigService.class);

        session.getUserProperties().put("accessToken", accessToken);
        session.getUserProperties().put("hostId", hostId);
        session.getUserProperties().put("cols", cols);
        session.getUserProperties().put("rows", rows);


        SessionStorage.sendMessage(session, MessageResponse.Code.info, this.getBanner().toString());


        OauthInfo oauthInfo = oauthService.getOAuth(accessToken);
        if (oauthInfo == null) {
            SessionStorage.sendMessage(session, MessageResponse.Code.info, "您的登录信息已经失效，请重新登录");
            session.close();
            return;
        }

        HostInfo hostInfo = hostInfoService.findById(Long.parseLong(hostId));
        if (hostInfo == null) {
            SessionStorage.sendMessage(session, MessageResponse.Code.info, "您要登录的主机已经不存在");
            session.close();
            return;
        }

        List<Long> listGroupUsers = hostGroupService.findUserIds(hostInfo.getGroupId());
        if (listGroupUsers == null || !listGroupUsers.contains(oauthInfo.getId())) {
            SessionStorage.sendMessage(session, MessageResponse.Code.info, "您没有被授权管理此主机，请不要随意操作");
            session.close();
            return;
        }

        HostAuth hostAuth = hostAuthService.findById(hostInfo.getAuthId());
        if (hostAuth == null) {
            SessionStorage.sendMessage(session, MessageResponse.Code.info, "您要登录的主机密钥已经不存在");
            session.close();
            return;
        }

        String desKey = sysConfigService.getStringValue("fortGateway", "hostManager", "des_key");


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
            SessionStorage.sendMessage(session, MessageResponse.Code.info, "连接服务器失败！！");
            session.close();
            return;
        }
        SessionStorage.add(session.getId(), client);

        ShellOutPutTask task = new ShellOutPutTask(session, "UTF-8");

        task.start();
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        log.debug(String.format("%s断开连接", session.getId()));
        SessionStorage.remove(session.getId());
        SSHClient client = SessionStorage.get(session.getId());
        if (client != null) {
            client.close();
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param payload 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String payload, Session session) throws Exception {
        log.debug(payload);
        MessageReceive receive = JSONObject.parseObject(payload, MessageReceive.class);

        if (MessageReceive.Cmd.sh.equals(receive.getCmd())) {
            SSHClient client = SessionStorage.get(session.getId());

            if (client != null) {
                client.write(receive.getData());
            }
        }

        if (MessageReceive.Cmd.heartbeat.equals(receive.getCmd())) {
            OauthService oauthService = SpringBeanFactory.getApplicationContext().getBean(OauthService.class);

            String accessToken = session.getUserProperties().get("accessToken").toString();

            OauthInfo oauthInfo = oauthService.getOAuth(accessToken);
            MessageResponse response = MessageResponse.instance(MessageResponse.Code.heartbeat, oauthInfo.getAccessToken());
            SessionStorage.sendMessage(session, MessageResponse.Code.heartbeat, oauthInfo.getAccessToken());
        }

        if (MessageReceive.Cmd.resize.equals(receive.getCmd())) {
//            SSHClient client = SessionStorage.get(session.getId());
//            if (client != null) {
//                JSONObject jsonObject = JSON.parseObject(receive.getData());
//                client.setWindow(jsonObject.getInteger("cols"), jsonObject.getInteger("rows"));
//            }
        }
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error(String.format("%s/%s发生错误", session.getUserProperties().get("uid").toString(), session.getUserProperties().get("accessToken").toString()), error);
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
