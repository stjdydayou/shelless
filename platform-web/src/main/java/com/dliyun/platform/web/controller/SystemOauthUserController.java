package com.dliyun.platform.web.controller;


import com.dliyun.platform.common.DwzJSON;
import com.dliyun.platform.common.DwzPageInfo;
import com.dliyun.platform.common.ServletContext;
import com.dliyun.platform.common.exception.NoFoundException;
import com.dliyun.platform.common.exception.ServiceException;
import com.dliyun.platform.common.oauth.OauthService;
import com.dliyun.platform.common.oauth.Permission;
import com.dliyun.platform.common.paginator.domain.PageResult;
import com.dliyun.platform.common.utils.DateUtil;
import com.dliyun.platform.common.utils.RandCodeUtil;
import com.dliyun.platform.core.model.SystemOauthRoleInfo;
import com.dliyun.platform.core.model.SystemOauthUserBaseInfo;
import com.dliyun.platform.core.model.SystemOauthUserLoginAccount;
import com.dliyun.platform.core.model.SystemOauthUserPassword;
import com.dliyun.platform.core.service.AbstractSysConfigService;
import com.dliyun.platform.core.service.SystemOauthRoleInfoService;
import com.dliyun.platform.core.service.SystemOauthUserInfoService;
import com.dliyun.platform.core.vo.SystemOauthUserInfoVO;
import com.dliyun.platform.web.params.SaveUserInfoParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
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

    @Autowired
    private AbstractSysConfigService sysConfigService;

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
        String loginPasswd = this.sysConfigService.getStringValue("system", "oauth", "default_password");
        if (ids != null) {
            for (Long id : ids) {
                String salt = RandCodeUtil.get(6, false);

                String saltPasswd = this.oauthService.generatePassword(loginPasswd, salt);
                this.systemOauthUserInfoService.insertOrUpdateUserPassword(id, saltPasswd, salt, SystemOauthUserPassword.UserPasswordType.login);
            }
        }
        return DwzJSON.body(DwzJSON.StatusCode.success).setMessage("用户密码已经成功被重置为" + loginPasswd).setTabid("system", "oauth", "user");
    }

    @Permission(pluginKey = "system", moduleKey = "oauth", authority = "user.roles")
    @RequestMapping("/roles.htm")
    public String roles(ModelMap modelMap, Long id) throws NoFoundException {
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
    public DwzJSON roles(Long uid, Long[] roleIds) throws ServiceException {
        this.systemOauthUserInfoService.saveRoles(uid, roleIds);
        return DwzJSON.body(DwzJSON.StatusCode.success).setMessage("设置成功").setCloseCurrent(true).setTabid("system", "oauth", "user");
    }

    @Permission(pluginKey = "system", moduleKey = "oauth", authority = "user.add")
    @RequestMapping("/add.htm")
    public String add() {
        return "/system/oauth/user/add";
    }

    @ResponseBody
    @Permission(pluginKey = "system", moduleKey = "oauth", authority = "user.add")
    @RequestMapping("/add.ajax")
    public DwzJSON add(@Valid SaveUserInfoParam param) throws ServiceException {

        SystemOauthUserLoginAccount userNameAccount = this.systemOauthUserInfoService.findLoginAccount(param.getMp(), SystemOauthUserLoginAccount.AccountType.userName);
        if (userNameAccount != null) {
            return DwzJSON.body(DwzJSON.StatusCode.error).setMessage("登录账号已被其他用户使用，请使更换手机号");
        }

        SystemOauthUserLoginAccount mpAccount = this.systemOauthUserInfoService.findLoginAccount(param.getMp(), SystemOauthUserLoginAccount.AccountType.mp);
        if (mpAccount != null) {
            return DwzJSON.body(DwzJSON.StatusCode.error).setMessage("手机号已被其他用户使用，请使更换手机号");
        }

        SystemOauthUserLoginAccount emailpAccount = this.systemOauthUserInfoService.findLoginAccount(param.getEmail(), SystemOauthUserLoginAccount.AccountType.email);
        if (emailpAccount != null) {
            return DwzJSON.body(DwzJSON.StatusCode.error).setMessage("邮箱已被其他用户使用，请使更换邮箱");
        }


        if (param.getGender() == null) {
            param.setGender(SystemOauthUserBaseInfo.Gender.secret);
        }

        String loginPassword = this.sysConfigService.getStringValue("system", "oauth", "default_password");

        String salt = RandCodeUtil.getSalt();
        String secretPassword = this.oauthService.generatePassword(loginPassword, salt);

        SystemOauthUserBaseInfo userInfo = new SystemOauthUserBaseInfo();
        userInfo.setNickName(param.getNickName());
        userInfo.setState(SystemOauthUserBaseInfo.UserState.normal);
        userInfo.setGender(param.getGender());
        userInfo.setRegisterTime(DateUtil.current());
        userInfo.setRegisterIp(ServletContext.getRemoteIPAddress());

        List<SystemOauthUserLoginAccount> loginAccounts = new ArrayList<>();
        loginAccounts.add(SystemOauthUserLoginAccount.instance(param.getUserName(), SystemOauthUserLoginAccount.AccountType.userName));
        loginAccounts.add(SystemOauthUserLoginAccount.instance(param.getMp(), SystemOauthUserLoginAccount.AccountType.mp));
        loginAccounts.add(SystemOauthUserLoginAccount.instance(param.getEmail(), SystemOauthUserLoginAccount.AccountType.email));
        this.systemOauthUserInfoService.register(userInfo, secretPassword, salt, loginAccounts);

        return DwzJSON.body(DwzJSON.StatusCode.success).setMessage("保存用户成功").setCloseCurrent(true).setTabid("system", "oauth", "user");
    }
}
