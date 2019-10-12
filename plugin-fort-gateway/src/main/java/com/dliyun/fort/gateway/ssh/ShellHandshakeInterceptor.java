package com.dliyun.fort.gateway.ssh;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

            if (StringUtils.isBlank(accessToken) || StringUtils.isBlank(hostId) || StringUtils.isBlank(cols) || StringUtils.isBlank(rows)) {
                return false;
            }

            attributes.put("accessToken", accessToken);
            attributes.put("hostId", hostId);
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
