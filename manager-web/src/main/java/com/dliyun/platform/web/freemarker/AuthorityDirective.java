package com.dliyun.platform.web.freemarker;

import com.dliyun.platform.common.freemarker.FreemarkerComponent;
import com.dliyun.platform.common.oauth.OauthInfo;
import com.dliyun.platform.common.oauth.OauthService;
import freemarker.core.Environment;
import freemarker.template.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.util.Map;

@Slf4j
@FreemarkerComponent("authorityDirective")
public class AuthorityDirective implements TemplateDirectiveModel {

    @Autowired
    private OauthService authService;

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        boolean hasAuthority = false;

        String pluginKey = params.get("pluginKey") == null ? null : params.get("pluginKey").toString().trim();
        String moduleKey = params.get("moduleKey") == null ? null : params.get("moduleKey").toString().trim();
        String authorities = params.get("authorities") == null ? null : params.get("authorities").toString().trim();

        if (StringUtils.isNotBlank(pluginKey) && StringUtils.isNotBlank(moduleKey)) {
            OauthInfo oauthInfo = this.authService.getOAuth();

            if (StringUtils.isNotBlank(authorities)) {
                String[] arrayAuthorities = authorities.split(";");


                if (arrayAuthorities.length > 0) {
                    for (String authority : arrayAuthorities) {

                        if (oauthInfo.hasAuthority(pluginKey, moduleKey, authority)) {
                            hasAuthority = true;
                            break;
                        }
                    }
                } else {
                    hasAuthority = true;
                }
            } else if (oauthInfo != null) {
                hasAuthority = true;
            }
        } else {
            log.warn("s.auth 中pluginKey,moduleKey必填");
        }
        DefaultObjectWrapper defaultObjectWrapper = new DefaultObjectWrapper(Configuration.VERSION_2_3_23);
        env.setVariable("hasAuthority", defaultObjectWrapper.wrap(hasAuthority));
        body.render(env.getOut());
    }

}
