package com.dliyun.platform.web.controller;

import com.dliyun.platform.PluginInfo;
import com.dliyun.platform.common.DwzJSON;
import com.dliyun.platform.common.ServletContext;
import com.dliyun.platform.common.exception.ServiceException;
import com.dliyun.platform.common.oauth.OauthInfo;
import com.dliyun.platform.common.oauth.OauthService;
import com.dliyun.platform.common.oauth.Permission;
import com.dliyun.platform.common.plugin.PluginMenu;
import com.dliyun.platform.common.plugin.PluginModuleInfo;
import com.dliyun.platform.common.service.SimpleCaptchaService;
import com.dliyun.platform.common.utils.DateUtil;
import com.dliyun.platform.common.utils.PatternUtils;
import com.dliyun.platform.common.utils.RandCodeUtil;
import com.dliyun.platform.core.model.SystemOauthUserBaseInfo;
import com.dliyun.platform.core.model.SystemOauthUserLoginAccount;
import com.dliyun.platform.core.model.SystemOauthUserLoginLog;
import com.dliyun.platform.core.model.SystemOauthUserPassword;
import com.dliyun.platform.core.service.SystemOauthUserInfoService;
import com.dliyun.platform.web.AjaxResult;
import com.dliyun.platform.web.params.ChangeMyLoginPassowdParam;
import com.dliyun.platform.web.params.LoginParam;
import com.dliyun.platform.web.params.SaveUserInfoParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.*;

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

    @RequestMapping("/logout.htm")
    public String logout() {
        this.oauthService.destroy();
        return "redirect:/index.htm";
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

    @Permission(pluginKey = "system", moduleKey = "index")
    @GetMapping("/changeMyLoginPassowd.htm")
    public String changeMyLoginPassowd() {
        return "system/changeMyLoginPassowd";
    }


    @Permission(pluginKey = "system", moduleKey = "index")
    @ResponseBody
    @PostMapping("/changeMyLoginPassowd.ajax")
    public DwzJSON changeMyLoginPassowd(@Valid ChangeMyLoginPassowdParam param) {

        OauthInfo oauthInfo = this.oauthService.getOAuth();

        SystemOauthUserPassword userPassword = this.systemOauthUserInfoService.findUserPasswd(oauthInfo.getId(), SystemOauthUserPassword.UserPasswordType.login);

        String loginPassword = this.oauthService.generatePassword(param.getOldPassword(), userPassword.getSalt());
        log.debug(loginPassword);
        if (loginPassword.equals(userPassword.getPasswd())) {

            if (!param.getNewPassword().equals(param.getReNewPassword())) {
                return DwzJSON.body(DwzJSON.StatusCode.error, "确认密码与新密码不一致，请重新输入");
            }

            String salt = RandCodeUtil.get(6, false);

            String saltPasswd = this.oauthService.generatePassword(param.getNewPassword(), salt);

            this.systemOauthUserInfoService.insertOrUpdateUserPassword(oauthInfo.getId(), saltPasswd, salt, SystemOauthUserPassword.UserPasswordType.login);

            return DwzJSON.body(DwzJSON.StatusCode.success, "修改密码成功，下次请使用新的密码登录").setCloseCurrent(true);
        } else {
            return DwzJSON.body(DwzJSON.StatusCode.error, "旧的登录密码错误，请重新输入");
        }
    }

    @Permission(pluginKey = "system", moduleKey = "index")
    @GetMapping("/changeMyProfile.htm")
    public String changeMyProfile(ModelMap modelMap) {
        OauthInfo oauthInfo = this.oauthService.getOAuth();

        SystemOauthUserBaseInfo baseInfo = this.systemOauthUserInfoService.findUserBaseInfoById(oauthInfo.getId());
        List<SystemOauthUserLoginAccount> listAccounts = this.systemOauthUserInfoService.findLoginAccountsByUid(oauthInfo.getId());
        modelMap.addAttribute("baseInfo", baseInfo);
        Map<String, String> accounts = new HashMap<>(listAccounts.size());
        for (SystemOauthUserLoginAccount account : listAccounts) {
            accounts.put(account.getAccountType().name(), account.getLoginAccount());
        }
        modelMap.addAttribute("accounts", accounts);
        return "system/changeMyProfile";
    }


    @Permission(pluginKey = "system", moduleKey = "index")
    @ResponseBody
    @PostMapping("/changeMyProfile.ajax")
    public DwzJSON changeMyProfile(@Valid SaveUserInfoParam param) throws ServiceException {

        OauthInfo oauthInfo = this.oauthService.getOAuth();

        List<SystemOauthUserLoginAccount> loginAccounts = new ArrayList<>();

        if (StringUtils.isNotBlank(param.getMp())) {
            SystemOauthUserLoginAccount mpAccount = this.systemOauthUserInfoService.findLoginAccount(param.getMp(), SystemOauthUserLoginAccount.AccountType.mp);
            if (mpAccount != null && !mpAccount.getUid().equals(oauthInfo.getId())) {
                return DwzJSON.body(DwzJSON.StatusCode.error).setMessage("手机号已被其他用户使用，请使更换手机号");
            }
            loginAccounts.add(SystemOauthUserLoginAccount.instance(param.getMp(), SystemOauthUserLoginAccount.AccountType.mp));
        }

        if (StringUtils.isNotBlank(param.getEmail())) {
            SystemOauthUserLoginAccount emailpAccount = this.systemOauthUserInfoService.findLoginAccount(param.getEmail(), SystemOauthUserLoginAccount.AccountType.email);
            if (emailpAccount != null && !emailpAccount.getUid().equals(oauthInfo.getId())) {
                return DwzJSON.body(DwzJSON.StatusCode.error).setMessage("邮箱已被其他用户使用，请使更换邮箱");
            }
            loginAccounts.add(SystemOauthUserLoginAccount.instance(param.getEmail(), SystemOauthUserLoginAccount.AccountType.email));
        }


        if (param.getGender() == null) {
            param.setGender(SystemOauthUserBaseInfo.Gender.secret);
        }


        SystemOauthUserBaseInfo userInfo = new SystemOauthUserBaseInfo();
        userInfo.setId(oauthInfo.getId());
        userInfo.setNickName(param.getNickName());
        userInfo.setGender(param.getGender());


        this.systemOauthUserInfoService.updateBaseInfo(userInfo, loginAccounts);

        return DwzJSON.body(DwzJSON.StatusCode.success).setMessage("保存用户成功").setCloseCurrent(true).setTabid("system", "oauth", "user");
    }
}
