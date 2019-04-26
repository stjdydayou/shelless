package com.dliyun.fort.gateway.web.controller;

import com.dliyun.fort.gateway.core.model.HostGroup;
import com.dliyun.fort.gateway.core.service.HostGroupService;
import com.dliyun.fort.gateway.web.params.SaveHostGroupParam;
import com.dliyun.platform.common.DwzJSON;
import com.dliyun.platform.common.exception.NoFoundException;
import com.dliyun.platform.common.exception.ServiceException;
import com.dliyun.platform.common.oauth.Permission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2018/12/7 17:38
 */
@Slf4j
@Controller
@RequestMapping("/fortGateway/group")
public class HostGroupController {

    @Autowired
    private HostGroupService hostGroupService;

    @Permission(pluginKey = "fortGateway", moduleKey = "hostManager", authority = "group.find")
    @GetMapping("/index.htm")
    public String index(ModelMap modelMap) {
        List<HostGroup> listData = hostGroupService.findAll();
        modelMap.addAttribute("listData", listData);
        return "fortGateway/host/group/index";
    }

    @Permission(pluginKey = "fortGateway", moduleKey = "hostManager", authority = {"group.add", "group.edit"})
    @GetMapping("/edit.htm")
    public String edit(Long id, ModelMap modelMap) throws NoFoundException {
        HostGroup hostGroup = new HostGroup();
        if (id != null) {
            hostGroup = this.hostGroupService.findById(id);
        }
        if (hostGroup == null) {
            throw new NoFoundException();
        }
        modelMap.addAttribute("hostGroup", hostGroup);
        return "fortGateway/host/group/edit";
    }


    @Permission(pluginKey = "fortGateway", moduleKey = "hostManager", authority = {"group.add", "group.edit"})
    @ResponseBody
    @PostMapping("/insertOrUpdate.ajax")
    public DwzJSON insertOrUpdate(Long id, @Valid SaveHostGroupParam param) {

        HostGroup hostGroup = new HostGroup();
        if (id != null && id.compareTo(0L) > 0) {
            hostGroup.setId(id);
        }
        hostGroup.setName(param.getName());
        hostGroup.setRemark(param.getRemark());

        this.hostGroupService.insertOrUpdate(hostGroup);
        return DwzJSON.body(DwzJSON.StatusCode.success, "保存分组成功").setCloseCurrent(true).setTabid("fortGateway","hostManager","hostGroup");
    }

    @Permission(pluginKey = "fortGateway", moduleKey = "hostManager", authority = "host.delete")
    @ResponseBody
    @PostMapping("/delete.ajax")
    public DwzJSON delete(Long[] ids) throws ServiceException {
        this.hostGroupService.delete(ids);
        return DwzJSON.body(DwzJSON.StatusCode.success, "删除分组成功");
    }
}
