package com.dliyun.platform.web.controller;


import com.dliyun.platform.common.DwzJSON;
import com.dliyun.platform.common.DwzPageInfo;
import com.dliyun.platform.common.exception.NoFoundException;
import com.dliyun.platform.common.exception.ServiceException;
import com.dliyun.platform.common.oauth.OauthService;
import com.dliyun.platform.common.oauth.Permission;
import com.dliyun.platform.common.paginator.domain.PageResult;
import com.dliyun.platform.common.utils.RandCodeUtil;
import com.dliyun.platform.core.model.SystemOauthRoleInfo;
import com.dliyun.platform.core.model.SystemOauthUserBaseInfo;
import com.dliyun.platform.core.model.SystemOauthUserPassword;
import com.dliyun.platform.core.service.SystemOauthRoleInfoService;
import com.dliyun.platform.core.service.SystemOauthUserInfoService;
import com.dliyun.platform.core.vo.SystemOauthUserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shenyj on 17-3-24.
 */
@Slf4j
@Controller
@RequestMapping("/system/oauth/user")
public class SystemOauthUserController {

    @Autowired
    private SystemOauthUserInfoService systemOauthUserInfoService;

    @Autowired
    private SystemOauthRoleInfoService systemOauthRoleInfoService;

    @Autowired
    private OauthService oauthService;

    @Permission(pluginKey = "system", moduleKey = "oauth", authority = "user.find")
    @RequestMapping("/index.htm")
    public String user(ModelMap modelMap, DwzPageInfo dwzPageInfo, SystemOauthUserInfoVO vo) {
        PageResult<SystemOauthUserBaseInfo> pageResult = this.systemOauthUserInfoService.findPage(dwzPageInfo.getPageBounds(), vo);
        List<Map<String, Object>> listData = new ArrayList<>();

        for (SystemOauthUserBaseInfo userBaseInfo : pageResult.getData()) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("userBaseInfo", userBaseInfo);
            resultMap.put("loginAccounts", this.systemOauthUserInfoService.findLoginAccountsByUid(userBaseInfo.getId()));
            resultMap.put("listRoleIds", this.systemOauthUserInfoService.findRolesIdByUserId(userBaseInfo.getId()));
            listData.add(resultMap);
        }

        List<SystemOauthRoleInfo> listAllRoleData = this.systemOauthRoleInfoService.findAll();
        modelMap.addAttribute("listAllRoleData", listAllRoleData);

        modelMap.addAttribute("listData", listData);
        modelMap.addAttribute("paginator", pageResult.getPaginator());
        modelMap.addAttribute("vo", vo);
        modelMap.addAttribute("dwzPageInfo", dwzPageInfo);
        modelMap.addAttribute("userStateArray", SystemOauthUserBaseInfo.UserState.values());
        return "/system/oauth/user/index";
    }


    @Permission(pluginKey = "system", moduleKey = "oauth", authority = "user.disable")
    @ResponseBody
    @RequestMapping("/disable.ajax")
    public DwzJSON disable(Long[] ids) {
        if (ids != null) {
            for (Long id : ids) {
                if (id.equals(1000L)) {
                    continue;
                }
                SystemOauthUserBaseInfo userBaseInfo = new SystemOauthUserBaseInfo();
                userBaseInfo.setId(id);
                userBaseInfo.setState(SystemOauthUserBaseInfo.UserState.disable);
                this.systemOauthUserInfoService.updateBaseInfo(userBaseInfo);
            }
        }
        return DwzJSON.body(DwzJSON.StatusCode.success).setMessage("禁用成功");
    }

    @Permission(pluginKey = "system", moduleKey = "oauth", authority = "user.enable")
    @ResponseBody
    @RequestMapping("/enable.ajax")
    public DwzJSON enable(Long[] ids) {
        if (ids != null) {
            for (Long id : ids) {
                SystemOauthUserBaseInfo userBaseInfo = new SystemOauthUserBaseInfo();
                userBaseInfo.setId(id);
                userBaseInfo.setState(SystemOauthUserBaseInfo.UserState.normal);
                this.systemOauthUserInfoService.updateBaseInfo(userBaseInfo);
            }
        }
        return DwzJSON.body(DwzJSON.StatusCode.success).setMessage("启用成功");
    }

    @Permission(pluginKey = "system", moduleKey = "oauth", authority = "user.resetPassword")
    @ResponseBody
    @RequestMapping("/resetPassword.ajax")
    public DwzJSON resetPassword(Long[] ids) {
        if (ids != null) {
            for (Long id : ids) {
                String salt = RandCodeUtil.get(6, false);
                String loginPasswd = "111111";

                Map<String, String> paramData = new HashMap<>();
                paramData.put("password", loginPasswd);

                loginPasswd = this.oauthService.generatePassword(loginPasswd, salt);
                this.systemOauthUserInfoService.insertOrUpdateUserPassword(id, loginPasswd, salt, SystemOauthUserPassword.UserPasswordType.login);
            }
        }
        return DwzJSON.body(DwzJSON.StatusCode.success).setMessage("用户密码已经成功被重置为111111").setTabid("system", "oauth", "user");
    }

    @Permission(pluginKey = "system", moduleKey = "oauth", authority = "user.roles")
    @RequestMapping("/roles.htm")
    public String userSetRole(ModelMap modelMap, Long id) throws NoFoundException {
        SystemOauthUserBaseInfo userBaseInfo = this.systemOauthUserInfoService.findUserBaseInfoById(id);
        if (userBaseInfo == null) {
            throw new NoFoundException();
        }

        modelMap.addAttribute("userBaseInfo", userBaseInfo);

        modelMap.addAttribute("loginAccounts", this.systemOauthUserInfoService.findLoginAccountsByUid(userBaseInfo.getId()));


        List<SystemOauthRoleInfo> listAllRoles = this.systemOauthRoleInfoService.findAll();
        modelMap.addAttribute("listAllRoles", listAllRoles);

        List<Long> listRoles = this.systemOauthUserInfoService.findRolesIdByUserId(userBaseInfo.getId());
        modelMap.addAttribute("listRoles", listRoles);
        return "/system/oauth/user/roles";
    }

    @ResponseBody
    @Permission(pluginKey = "system", moduleKey = "oauth", authority = "user.roles")
    @RequestMapping("/roles.ajax")
    public DwzJSON userSetRole(Long uid, Long[] roleIds) throws ServiceException {
        this.systemOauthUserInfoService.saveRoles(uid, roleIds);
        return DwzJSON.body(DwzJSON.StatusCode.success).setMessage("设置成功").setCloseCurrent(true).setTabid("system", "oauth", "user");
    }
}
