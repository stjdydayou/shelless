package com.axungu.platform.web.controller;

import com.axungu.common.ServletContext;
import com.axungu.common.oauth.OauthInfo;
import com.axungu.common.oauth.OauthService;
import com.axungu.common.oauth.Permission;
import com.axungu.common.plugin.PluginMenu;
import com.axungu.common.plugin.PluginModuleInfo;
import com.axungu.common.service.SimpleCaptchaService;
import com.axungu.common.utils.DateUtil;
import com.axungu.common.utils.PatternUtils;
import com.axungu.platform.PluginInfo;
import com.axungu.platform.core.model.SystemOauthUserBaseInfo;
import com.axungu.platform.core.model.SystemOauthUserLoginAccount;
import com.axungu.platform.core.model.SystemOauthUserLoginLog;
import com.axungu.platform.core.model.SystemOauthUserPassword;
import com.axungu.platform.core.service.SystemOauthUserInfoService;
import com.axungu.platform.web.AjaxResult;
import com.axungu.platform.web.params.LoginParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
public class IndexController {

    @Autowired
    private SimpleCaptchaService simpleCaptchaService;

    @Autowired
    private SystemOauthUserInfoService systemOauthUserInfoService;

    @Autowired
    private OauthService oauthService;

    @Permission(pluginKey = "system", moduleKey = "index")
    @GetMapping({"/index.htm", "/"})
    public String index(ModelMap modelMap) {
        Collection<PluginInfo> plugins = PluginInfo.REGISTERED_PLUGINS.values();

        List<PluginInfo> registeredPlugins = new ArrayList<>();
        OauthInfo oauthInfo = this.oauthService.getOAuth();

        for (PluginInfo pluginInfo : plugins) {
            boolean hasModule = false;
            for (PluginModuleInfo moduleInfo : pluginInfo.getListModules()) {
                boolean hasMenu = false;
                for (PluginMenu menu : moduleInfo.getListMenus()) {
                    if (oauthInfo.hasAuthority(pluginInfo.getKey(), moduleInfo.getKey(), menu.getAuthority())) {
                        hasMenu = true;
                        break;
                    }
                }
                if (hasMenu) {
                    hasModule = true;
                    break;
                }
            }
            if (hasModule) {
                registeredPlugins.add(pluginInfo);
            }
        }
        modelMap.addAttribute("registeredPlugins", registeredPlugins);

        return "index";
    }

    @Permission(pluginKey = "system", moduleKey = "index")
    @GetMapping("/index_body.htm")
    public String indexBody() {
        return "index_body";
    }

    @GetMapping("/login.htm")
    public String login(ModelMap modelMap) {
        return "login";
    }

    @ResponseBody
    @PostMapping("/login.ajax")
    public AjaxResult login(@Valid LoginParam loginParam) {
        String accessToken = ServletContext.getAccessToken();


        SystemOauthUserLoginAccount.AccountType accountType = SystemOauthUserLoginAccount.AccountType.userName;
        if (PatternUtils.validExpression(loginParam.getLoginAccount(), PatternUtils.MOBILE_EXPRESSION)) {
            accountType = SystemOauthUserLoginAccount.AccountType.mp;
        }
        if (PatternUtils.validExpression(loginParam.getLoginAccount(), PatternUtils.EMAIL_EXPRESSION)) {
            accountType = SystemOauthUserLoginAccount.AccountType.email;
        }

        if (!this.simpleCaptchaService.compareCaptcha(loginParam.getCaptcha(), accessToken, true)) {
            return AjaxResult.instance("验证码输入不正确");
        }


        SystemOauthUserLoginAccount userLoginAccount = this.systemOauthUserInfoService.findLoginAccount(loginParam.getLoginAccount(), accountType);
        if (userLoginAccount == null) {
            return AjaxResult.instance("用户名或密码错误，请重新输入");
        } else {
            SystemOauthUserBaseInfo userBaseInfo = this.systemOauthUserInfoService.findUserBaseInfoById(userLoginAccount.getUid());

            SystemOauthUserPassword userPassword = this.systemOauthUserInfoService.findUserPasswd(userBaseInfo.getId(), SystemOauthUserPassword.UserPasswordType.login);

            String loginPassword = this.oauthService.generatePassword(loginParam.getLoginPassword(), userPassword.getSalt());
            log.debug(loginPassword);
            if (loginPassword.equals(userPassword.getPasswd())) {


                SystemOauthUserLoginLog lastLoginLog = this.systemOauthUserInfoService.findLastLogin(userBaseInfo.getId());
                Date lastLoginTime = lastLoginLog == null ? DateUtil.current() : lastLoginLog.getLoginTime();

                List<String> listAuthorities = this.systemOauthUserInfoService.findAuthorities(userBaseInfo.getId());

                OauthInfo oauthInfo = new OauthInfo(userBaseInfo.getId(), userBaseInfo.getNickName(), userBaseInfo.getAvatar(), lastLoginTime, accessToken, listAuthorities);
                this.oauthService.setAuth(oauthInfo);

                try {
                    this.systemOauthUserInfoService.updateLoginInfo(userBaseInfo.getId(), ServletContext.getRemoteIPAddress());
                } catch (Exception e) {
                    log.warn("save login log error", e);
                }


                return AjaxResult.instance(AjaxResult.Code.SUCCESS).setData(oauthInfo);
            } else {
                return AjaxResult.instance("用户名或密码错误，请重新输入");
            }
        }
    }

    @ResponseBody
    @GetMapping("/getCaptcha.ajax")
    public AjaxResult captcha() {
        String accessToken = ServletContext.getAccessToken();

        String captcha = simpleCaptchaService.generateCaptcha(accessToken);
        if (StringUtils.isNotBlank(captcha)) {
            return AjaxResult.instance(AjaxResult.Code.SUCCESS).setData(captcha);
        } else {
            return AjaxResult.instance(AjaxResult.Code.SERVER_ERROR, "获取验证码失败");
        }
    }
}
