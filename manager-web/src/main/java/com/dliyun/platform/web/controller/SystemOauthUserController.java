package com.dliyun.platform.web.controller;


import com.dliyun.platform.common.DwzJSON;
import com.dliyun.platform.common.DwzPageInfo;
import com.dliyun.platform.common.oauth.OauthService;
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
        return DwzJSON.body(DwzJSON.StatusCode.success).setMessage("用户密码已经成功被重置为111111").setTabid("system", "oauth", "oauth-user");
    }
}
