package com.axungu.platform.web.controller;

import com.axungu.common.ServletContext;
import com.axungu.common.oauth.OauthInfo;
import com.axungu.common.oauth.OauthService;
import com.axungu.common.service.SimpleCaptchaService;
import com.axungu.common.utils.DateUtil;
import com.axungu.common.utils.PatternUtils;
import com.axungu.platform.PluginInfo;
import com.axungu.platform.core.enums.AccountType;
import com.axungu.platform.core.enums.UserPasswordType;
import com.axungu.platform.core.model.UserBaseInfo;
import com.axungu.platform.core.model.UserLoginAccount;
import com.axungu.platform.core.model.UserLoginLog;
import com.axungu.platform.core.model.UserPassword;
import com.axungu.platform.core.service.UserInfoService;
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
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
public class IndexController {

    @Autowired
    private SimpleCaptchaService simpleCaptchaService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private OauthService oauthService;

    //    @Permission(pluginKey = "system")
    @GetMapping({"/index.htm", "/"})
    public String index(ModelMap modelMap) {
        modelMap.addAttribute("registeredPlugins", PluginInfo.REGISTERED_PLUGINS.values());
        return "index";
    }

    //    @Permission(pluginKey = "system")
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


        AccountType accountType = AccountType.userName;
        if (PatternUtils.validExpression(loginParam.getLoginAccount(), PatternUtils.MOBILE_EXPRESSION)) {
            accountType = AccountType.mp;
        }
        if (PatternUtils.validExpression(loginParam.getLoginAccount(), PatternUtils.EMAIL_EXPRESSION)) {
            accountType = AccountType.email;
        }

        if (!this.simpleCaptchaService.compareCaptcha(loginParam.getCaptcha(), accessToken, true)) {
            return AjaxResult.instance("验证码输入不正确");
        }


        UserLoginAccount userLoginAccount = this.userInfoService.findLoginAccount(loginParam.getLoginAccount(), accountType);
        if (userLoginAccount == null) {
            return AjaxResult.instance("用户名或密码错误，请重新输入");
        } else {
            UserBaseInfo userBaseInfo = this.userInfoService.findUserBaseInfoById(userLoginAccount.getUid());

            UserPassword userPassword = this.userInfoService.findUserPasswd(userBaseInfo.getId(), UserPasswordType.login);

            String loginPassword = this.oauthService.generatePassword(loginParam.getLoginPassword(), userPassword.getSalt());
            log.debug(loginPassword);
            if (loginPassword.equals(userPassword.getPasswd())) {


                UserLoginLog lastLoginLog = this.userInfoService.findLastLogin(userBaseInfo.getId());
                Date lastLoginTime = lastLoginLog == null ? DateUtil.current() : lastLoginLog.getLoginTime();

                List<String> listAuthorities = this.userInfoService.findAuthorities(userBaseInfo.getId());

                OauthInfo oauthInfo = new OauthInfo(userBaseInfo.getId(), userBaseInfo.getNickName(), userBaseInfo.getAvatar(), lastLoginTime, accessToken, listAuthorities);
                this.oauthService.setAuth(oauthInfo);

                try {
                    this.userInfoService.updateLoginInfo(userBaseInfo.getId(), ServletContext.getRemoteIPAddress());
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
