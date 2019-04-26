package com.dliyun.fort.gateway;


import com.dliyun.platform.common.plugin.*;
import com.dliyun.fort.gateway.ssh.ShellHandshakeInterceptor;
import com.dliyun.fort.gateway.ssh.ShellWebSocketHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统启动配置
 *
 * @author jtoms
 */
@EnableWebSocket
@MapperScan(basePackages = "com.dliyun.fort.gateway.core.mappers")
@Plugin(key = "fortGateway", name = "WEB跳板机", faicon = "server")
public class Config implements RegisterPlugin, WebSocketConfigurer {


    @Autowired
    private ShellHandshakeInterceptor shellHandshakeInterceptor;

    @Autowired
    private ShellWebSocketHandler shellWebSocketHandler;


    @Override
    public List<PluginModuleInfo> registerModule() {

        List<PluginModuleInfo> listPluginModules = new ArrayList<>();

        listPluginModules.add(buildHostModuleInfo());

        return listPluginModules;
    }

    private PluginModuleInfo buildHostModuleInfo() {
        PluginModuleInfo moduleInfo = new PluginModuleInfo("hostManager", "服务器管理", "globe");

        //注册菜单
        moduleInfo.add(new PluginMenu("hostGroup", "分组管理", "/fortGateway/group/index.htm", "group.find"));
        moduleInfo.add(new PluginMenu("host-auth", "密钥管理", "/fortGateway/auth/index.htm", "auth.find"));
        moduleInfo.add(new PluginMenu("host-info", "主机管理", "/fortGateway/hostInfo/index.htm", "host.find"));

        //注册权限
        moduleInfo.add(new PluginAuthority("group.find", "查询分组"));
        moduleInfo.add(new PluginAuthority("group.add", "添加分组"));
        moduleInfo.add(new PluginAuthority("group.edit", "修改分组"));
        moduleInfo.add(new PluginAuthority("group.delete", "删除分组"));
        moduleInfo.add(new PluginAuthority("group.authority", "分配分组用户"));

        moduleInfo.add(new PluginAuthority("auth.find", "查询密钥"));
        moduleInfo.add(new PluginAuthority("auth.add", "添加密钥"));
        moduleInfo.add(new PluginAuthority("auth.edit", "修改密钥"));
        moduleInfo.add(new PluginAuthority("auth.delete", "删除密钥"));

        moduleInfo.add(new PluginAuthority("host.find", "查询主机"));
        moduleInfo.add(new PluginAuthority("host.add", "添加主机"));
        moduleInfo.add(new PluginAuthority("host.edit", "修改主机"));
        moduleInfo.add(new PluginAuthority("host.delete", "删除主机"));
        moduleInfo.add(new PluginAuthority("host.terminal", "打开主机终端"));


        //注册参数配置
        moduleInfo.add(new PluginConfig("des_key", "服务器密码加密KEY", PluginConfig.ConfigType.string));

        return moduleInfo;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(shellWebSocketHandler, "/terminal/socket")
                .setAllowedOrigins("*")
                .addInterceptors(shellHandshakeInterceptor);
    }
}

