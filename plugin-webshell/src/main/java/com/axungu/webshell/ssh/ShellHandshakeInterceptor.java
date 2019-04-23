package com.axungu.webshell.ssh;

import com.dliyun.platform.common.oauth.OauthInfo;
import com.dliyun.platform.common.oauth.OauthService;
import com.axungu.webshell.core.model.HostAuth;
import com.axungu.webshell.core.model.HostInfo;
import com.axungu.webshell.core.service.HostAuthService;
import com.axungu.webshell.core.service.HostInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2018/12/6 00:25
 */
@Slf4j
@Component
public class ShellHandshakeInterceptor implements HandshakeInterceptor {

    @Autowired
    private OauthService oauthService;

    @Autowired
    private HostInfoService hostInfoService;

    @Autowired
    private HostAuthService hostAuthService;

    /**
     * @param request
     * @param response
     * @param handler
     * @param attributes
     * @return
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler, Map<String, Object> attributes) {
        try {
            String accessToken = ((ServletServerHttpRequest) request).getServletRequest().getParameter("accessToken");
            String hostId = ((ServletServerHttpRequest) request).getServletRequest().getParameter("hostId");
            String cols = ((ServletServerHttpRequest) request).getServletRequest().getParameter("cols");
            String rows = ((ServletServerHttpRequest) request).getServletRequest().getParameter("rows");

            OauthInfo oauthInfo = this.oauthService.getOAuth(accessToken);
            if (oauthInfo == null) {
                return false;
            }

            HostInfo hostInfo = this.hostInfoService.findById(Long.parseLong(hostId));
            if (hostInfo == null) {
                return false;
            }

            HostAuth hostAuth = hostAuthService.findById(hostInfo.getAuthId());
            if (hostAuth == null) {
                return false;
            }

            attributes.put("hostInfo", hostInfo);
            attributes.put("hostAuth", hostAuth);
            attributes.put("cols", cols);
            attributes.put("rows", rows);

            return true;
        } catch (Exception e) {
            log.error("beforeHandshake error", e);
            return false;
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler, Exception e) {

    }

}
