package com.axungu.platform.core.oauth;

import com.alibaba.fastjson.JSON;
import com.axungu.common.ServletContext;
import com.axungu.common.oauth.OauthInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OauthServiceEhCacheImpl extends BaseOauthService {

    @Autowired
    private CacheManager cacheManager;

    @Override
    public void setAuth(OauthInfo oauthInfo) {
        try {
            String accessToken = ServletContext.getAccessToken();

            String jsonString = JSON.toJSONString(oauthInfo);

            Cache<String, String> cache = cacheManager.getCache("oauthSessionCache", String.class, String.class);
            cache.put(accessToken, jsonString);

        } catch (Exception e) {
            log.error("save oauth error", e);
        }

    }

    @Override
    public OauthInfo getOAuth() {
        String accessToken = ServletContext.getAccessToken();
        return getOAuth(accessToken);
    }

    @Override
    public OauthInfo getOAuth(String accessToken) {
        try {
            if (!StringUtils.isBlank(accessToken)) {
                Cache<String, String> cache = cacheManager.getCache("oauthSessionCache", String.class, String.class);
                String json = cache.get(accessToken);
                if (json != null) {
                    OauthInfo oauthInfo = JSON.parseObject(json, OauthInfo.class);
                    if (oauthInfo != null && accessToken.equals(oauthInfo.getAccessToken())) {
                        return oauthInfo;
                    }
                }
            }
        } catch (Exception e) {
            log.error("get oauth error", e);
        }
        return null;
    }


    @Override
    public void destroy() {
        //销毁登录信息
        String accessToken = ServletContext.getAccessToken();

        Cache<String, String> cache = cacheManager.getCache("oauthSessionCache", String.class, String.class);
        cache.remove(accessToken);
    }
}
