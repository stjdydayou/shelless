package com.dliyun.platform.core.oauth;


import com.dliyun.platform.common.oauth.OauthInfo;
import com.dliyun.platform.common.oauth.OauthService;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author jtoms
 */
public abstract class BaseOauthService implements OauthService {


    /**
     * 生成密码
     *
     * @param loginPwd
     * @param secret
     * @return
     */
    @Override
    public String generatePassword(String loginPwd, String secret) {
        if (loginPwd == null) {
            return "";
        }
        loginPwd = DigestUtils.sha256Hex(loginPwd);
        return DigestUtils.md5Hex(loginPwd + "@" + secret);
    }

    /**
     * 保存用户登录信息
     *
     * @param oauthInfo
     */
    @Override
    public abstract void setAuth(OauthInfo oauthInfo);

    /**
     * 获取用户登录信息
     *
     * @return
     */
    @Override
    public abstract OauthInfo getOAuth();


    /**
     * 获取用户登录信息
     *
     * @param accessToken
     * @return
     */
    @Override
    public abstract OauthInfo getOAuth(String accessToken);

    /**
     * 销毁用户登录信息
     */
    @Override
    public abstract void destroy();
}
