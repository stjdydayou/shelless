package com.axungu.common.oauth;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author jtoms
 */
public class OauthInfo implements Serializable {

    private static final long serialVersionUID = -7252878048497511413L;

    /**
     * 唯一标识
     */
    private Long id;

    /**
     * 用户的登录账号
     */
    private String nickName;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 用户登录令牌
     */
    private String accessToken;

    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 用户权限
     */
    private List<String> authorities;

    public OauthInfo() {
        super();
    }

    public OauthInfo(Long id, String nickName, String avatar, Date lastLoginTime, String accessToken, List<String> authorities) {
        this.id = id;
        this.nickName = nickName;
        this.avatar = avatar;
        this.lastLoginTime = lastLoginTime;
        this.accessToken = accessToken;
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public boolean hasAuthority(String pluginKey, String moduleKey, String authority) {
        if (StringUtils.isBlank(authority)) {
            return true;
        } else {
            String fullAuthority = String.format("%s.%s.%s", pluginKey, moduleKey, authority);
            return this.authorities != null && this.authorities.contains(fullAuthority);
        }
    }
}
