package com.dliyun.platform.core.oauth;

import com.alibaba.fastjson.JSON;
import com.dliyun.platform.common.ServletContext;
import com.dliyun.platform.common.oauth.OauthInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class OauthServiceRedisImpl extends BaseOauthService {

    private static final String OAUTH_REDIS_KEY = "system:oauth:info:%s";

    // 登录失效时间2小时
    private static final int EXPIRE = 2;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public void setAuth(OauthInfo oauthInfo) {
        try {
            String jsonString = JSON.toJSONString(oauthInfo);
            BoundValueOperations<String, String> boundValueOperations = this.stringRedisTemplate.boundValueOps(String.format(OAUTH_REDIS_KEY, oauthInfo.getAccessToken()));
            boundValueOperations.set(jsonString);
            boundValueOperations.expire(EXPIRE, TimeUnit.HOURS);
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
                BoundValueOperations<String, String> boundValueOperations = this.stringRedisTemplate.boundValueOps(String.format(OAUTH_REDIS_KEY, accessToken));
                String json = boundValueOperations.get();
                if (json != null) {
                    OauthInfo oauthInfo = JSON.parseObject(json, OauthInfo.class);
                    if (oauthInfo != null && accessToken.equals(oauthInfo.getAccessToken())) {
                        boundValueOperations.expire(EXPIRE, TimeUnit.HOURS);
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
        destroy(accessToken);
    }

    @Override
    public void destroy(String accessToken) {
        //销毁登录信息
        this.stringRedisTemplate.delete(String.format(OAUTH_REDIS_KEY, accessToken));
    }
}
