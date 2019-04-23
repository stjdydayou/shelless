package com.dliyun.platform.common.oauth;


/**
 * @author jtoms
 */
public interface OauthService {

    /**
     * 生成密码
     *
     * @param loginPwd
     * @param salt
     * @return
     */
    String generatePassword(String loginPwd, String salt);

    /**
     * 保存用户登录信息
     *
     * @param oauthInfo
     */
    void setAuth(OauthInfo oauthInfo);

    /**
     * 获取用户登录信息
     *
     * @return
     */
    OauthInfo getOAuth();

    /**
     * 获取用户登录信息
     *
     * @param accessToken
     * @return
     */
    OauthInfo getOAuth(String accessToken);

    /**
     * 销毁用户登录信息
     */
    void destroy();
}
