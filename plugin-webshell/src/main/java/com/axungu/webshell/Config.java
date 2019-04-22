package com.axungu.webshell;


import com.axungu.common.plugin.*;
import com.axungu.webshell.ssh.ShellHandshakeInterceptor;
import com.axungu.webshell.ssh.ShellWebSocketHandler;
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
@MapperScan(basePackages = "com.axungu.webshell.core.mappers")
@Plugin(key = "webshell", name = "WEB跳板机", faicon = "server")
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
        PluginModuleInfo moduleInfo = new PluginModuleInfo("服务器管理", "globe");

        //注册菜单
        List<PluginMenu> listMenus = new ArrayList<>();
        listMenus.add(new PluginMenu("host-group", "分组管理", "/host/group/index.htm"));
        listMenus.add(new PluginMenu("host-auth", "密钥管理", "/host/auth/index.htm"));
        listMenus.add(new PluginMenu("host-info", "主机管理", "/webshell/hostInfo/index.htm"));
        moduleInfo.setListMenus(listMenus);

        //注册权限
        List<PluginAuthority> listAuthorities = new ArrayList<>();
        listAuthorities.add(new PluginAuthority("group.find", "查询分组"));
        listAuthorities.add(new PluginAuthority("group.add", "添加分组"));
        listAuthorities.add(new PluginAuthority("group.edit", "修改分组"));
        listAuthorities.add(new PluginAuthority("group.delete", "删除分组"));
        listAuthorities.add(new PluginAuthority("group.delete", "分配分组用户"));

        listAuthorities.add(new PluginAuthority("auth.find", "查询密钥"));
        listAuthorities.add(new PluginAuthority("auth.add", "添加密钥"));
        listAuthorities.add(new PluginAuthority("auth.edit", "修改密钥"));
        listAuthorities.add(new PluginAuthority("auth.delete", "删除密钥"));

        listAuthorities.add(new PluginAuthority("host.find", "查询主机"));
        listAuthorities.add(new PluginAuthority("host.add", "添加主机"));
        listAuthorities.add(new PluginAuthority("host.edit", "修改主机"));
        listAuthorities.add(new PluginAuthority("host.delete", "删除主机"));
        listAuthorities.add(new PluginAuthority("host.delete", "打开主机终端"));


        moduleInfo.setListAuthorities(listAuthorities);

        //注册参数配置
        List<PluginConfig> listConfigs = new ArrayList<>();
        listConfigs.add(new PluginConfig("des_key", "服务器密码加密KEY", PluginConfig.ConfigType.string));
        moduleInfo.setListConfigs(listConfigs);

        return moduleInfo;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(shellWebSocketHandler, "/terminal/socket")
                .setAllowedOrigins("*")
                .addInterceptors(shellHandshakeInterceptor);
    }
}

