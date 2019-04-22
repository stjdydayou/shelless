package com.axungu.platform.core.oauth;

import com.axungu.common.exception.NoLoginException;
import com.axungu.common.exception.PermissionDeniedException;
import com.axungu.common.oauth.OauthInfo;
import com.axungu.common.oauth.OauthService;
import com.axungu.common.oauth.Permission;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jtoms
 */
@Component
public class OauthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private OauthService oauthService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws PermissionDeniedException, NoLoginException {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Permission permission = handlerMethod.getMethodAnnotation(Permission.class);

            if (permission != null) {
                OauthInfo oauthInfo = oauthService.getOAuth();
                if (oauthInfo == null || oauthInfo.getId() == null || StringUtils.isBlank(oauthInfo.getAccessToken())) {
                    throw new NoLoginException("/login.htm");
                }

                String[] arrayAuthorities = permission.value();
//                if (arrayAuthorities.length > 0) {
//                    if (oauthInfo.getAuthorities() == null || oauthInfo.getAuthorities().isEmpty()) {
//                        throw new PermissionDeniedException();
//                    }
//
//                    boolean hasAuthority = false;
//                    for (String authority : arrayAuthorities) {
//                        if (oauthInfo.getAuthorities().contains(authority.trim())) {
//                            hasAuthority = true;
//                            break;
//                        }
//                    }
//                    if (!hasAuthority) {
//                        throw new PermissionDeniedException();
//                    }
//                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }
}
