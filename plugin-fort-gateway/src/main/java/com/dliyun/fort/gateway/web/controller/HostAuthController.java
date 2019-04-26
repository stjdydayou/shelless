package com.dliyun.fort.gateway.web.controller;

import com.alibaba.fastjson.JSON;
import com.dliyun.fort.gateway.core.model.HostAuth;
import com.dliyun.fort.gateway.core.service.HostAuthService;
import com.dliyun.fort.gateway.ssh.CryptoDESUtil;
import com.dliyun.fort.gateway.web.params.SaveHostAuthParam;
import com.dliyun.platform.common.DwzJSON;
import com.dliyun.platform.common.exception.NoFoundException;
import com.dliyun.platform.common.exception.ServiceException;
import com.dliyun.platform.common.oauth.Permission;
import com.dliyun.platform.common.service.SysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2018/12/7 17:38
 */
@Slf4j
@Controller
@RequestMapping("/host/auth")
public class HostAuthController {

    @Autowired
    private HostAuthService hostAuthService;

    @Autowired
    private SysConfigService sysConfigService;

    @Permission(pluginKey = "fortGateway", moduleKey = "hostManager", authority = "auth.find")
    @GetMapping("/index.htm")
    public String index(ModelMap modelMap) {
        List<HostAuth> listData = hostAuthService.findAll();
        modelMap.addAttribute("listData", listData);
        return "fortGateway/host/auth/index";
    }

    @Permission(pluginKey = "fortGateway", moduleKey = "hostManager", authority = {"auth.add", "auth.auth"})
    @GetMapping("/edit.htm")
    public String edit(Long id, ModelMap modelMap) throws NoFoundException {
        HostAuth hostAuth = new HostAuth();
        if (id != null) {
            hostAuth = this.hostAuthService.findById(id);
        }
        if (hostAuth == null) {
            throw new NoFoundException();
        }
        modelMap.addAttribute("hostAuth", hostAuth);
        return "fortGateway/host/auth/edit";
    }


    @Permission(pluginKey = "fortGateway", moduleKey = "hostManager", authority = {"auth.add", "auth.auth"})
    @ResponseBody
    @PostMapping("/insertOrUpdate.ajax")
    public DwzJSON insertOrUpdate(Long id, SaveHostAuthParam param) throws ServiceException {
        String desKey = this.sysConfigService.getStringValue("fortGateway", "hostManager", "des_key");

        HostAuth hostAuth = new HostAuth();
        if (id != null && id.compareTo(0L) > 0) {
            hostAuth.setId(id);
        }
        hostAuth.setName(param.getName());
        hostAuth.setAuthType(param.getAuthType());
        hostAuth.setUserName(param.getUserName());
        hostAuth.setRemark(param.getRemark());

        if (param.getAuthType().equals(HostAuth.AuthType.password)) {
            if (StringUtils.isBlank(param.getPassword())) {
                return DwzJSON.body(DwzJSON.StatusCode.error, "密码不能为空");
            }
            hostAuth.setAuthText(CryptoDESUtil.encode(desKey, param.getPassword()));
        }

        if (param.getAuthType().equals(HostAuth.AuthType.sshkey)) {
            Map<String, String> authMap = new HashMap<>();
            authMap.put("privateKey", param.getPrivateKey());
            authMap.put("publicKey", param.getPublicKey());
            authMap.put("passphrase", param.getPassphrase());

            hostAuth.setAuthText(CryptoDESUtil.encode(desKey, JSON.toJSONString(authMap)));
        }


        this.hostAuthService.insertOrUpdate(hostAuth);
        return DwzJSON.body(DwzJSON.StatusCode.success, "保存密钥成功");
    }

    @Permission(pluginKey = "fortGateway", moduleKey = "hostManager", authority = "group.find")
    @ResponseBody
    @PostMapping("/delete.ajax")
    public DwzJSON delete(Long[] ids) throws ServiceException {
        this.hostAuthService.delete(ids);
        return DwzJSON.body(DwzJSON.StatusCode.success, "删除密钥成功");
    }
}
