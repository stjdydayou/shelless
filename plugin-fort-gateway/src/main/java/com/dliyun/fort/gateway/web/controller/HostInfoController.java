package com.dliyun.fort.gateway.web.controller;

import com.dliyun.fort.gateway.core.model.HostAuth;
import com.dliyun.fort.gateway.core.model.HostGroup;
import com.dliyun.fort.gateway.core.model.HostInfo;
import com.dliyun.fort.gateway.core.service.HostAuthService;
import com.dliyun.fort.gateway.core.service.HostGroupService;
import com.dliyun.fort.gateway.core.service.HostInfoService;
import com.dliyun.fort.gateway.core.vo.HostInfoVO;
import com.dliyun.platform.common.DwzPageInfo;
import com.dliyun.platform.common.ServletContext;
import com.dliyun.platform.common.exception.NoFoundException;
import com.dliyun.platform.common.exception.NoLoginException;
import com.dliyun.platform.common.oauth.OauthService;
import com.dliyun.platform.common.oauth.Permission;
import com.dliyun.platform.common.paginator.domain.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2018/12/7 17:38
 */
@Slf4j
@Controller
@RequestMapping("/fortGateway/hostInfo")
public class HostInfoController {

    @Autowired
    private HostInfoService hostInfoService;

    @Autowired
    private HostGroupService hostGroupService;

    @Autowired
    private HostAuthService hostAuthService;

    @Autowired
    private OauthService oauthService;

    @Permission(pluginKey = "fortGateway", moduleKey = "hostManager", authority = "host.find")
    @RequestMapping("/index.htm")
    public String index(ModelMap modelMap, DwzPageInfo dwzPageInfo, HostInfoVO vo) {

        PageResult<HostInfo> pageResult = hostInfoService.findPage(dwzPageInfo.getPageBounds(), vo);

        List<HostInfo> listData = pageResult.getData();
        for (HostInfo hostInfo : listData) {
            hostInfo.setHostGroup(this.hostGroupService.findById(hostInfo.getGroupId()));
            hostInfo.setHostAuth(this.hostAuthService.findById(hostInfo.getAuthId()));
        }
        modelMap.addAttribute("listData", pageResult.getData());
        modelMap.addAttribute("paginator", pageResult.getPaginator());
        modelMap.addAttribute("dwzPageInfo", dwzPageInfo);

        modelMap.addAttribute("vo", vo);

        List<HostAuth> listAuths = this.hostAuthService.findAll();
        modelMap.addAttribute("listAuths", listAuths);
        List<HostGroup> listGroups = this.hostGroupService.findAll();
        modelMap.addAttribute("listGroups", listGroups);
        return "fortGateway/host/info/index";
    }

    @Permission(pluginKey = "fortGateway", moduleKey = "hostManager", authority = "host.terminal")
    @RequestMapping("/terminal/{hostId:[0-9]+}.htm")
    public String terminal(@PathVariable Long hostId, ModelMap modelMap) throws NoLoginException, NoFoundException {
        modelMap.addAttribute("hostId", hostId);
        return "fortGateway/host/info/terminal";
    }

    @Permission(pluginKey = "fortGateway", moduleKey = "hostManager", authority = "host.terminal")
    @RequestMapping("/terminal/iframe/{hostId:[0-9]+}.htm")
    public String terminalIframe(@PathVariable Long hostId, ModelMap modelMap) throws NoLoginException, NoFoundException {
        HostInfo hostInfo = this.hostInfoService.findById(hostId);
        if (hostInfo == null) {
            throw new NoFoundException();
        }
        modelMap.addAttribute("hostId", hostId);
        modelMap.addAttribute("accessToken", ServletContext.getAccessToken());
        modelMap.addAttribute("hostInfo", hostInfo);
        return "fortGateway/host/info/terminal_iframe";
    }
}
