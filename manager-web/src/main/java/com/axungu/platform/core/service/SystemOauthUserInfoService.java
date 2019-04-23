package com.axungu.platform.core.service;

import com.axungu.platform.core.enums.AccountType;
import com.axungu.platform.core.enums.UserPasswordType;
import com.axungu.platform.core.model.SystemOauthUserBaseInfo;
import com.axungu.platform.core.model.SystemOauthUserLoginAccount;
import com.axungu.platform.core.model.SystemOauthUserLoginLog;
import com.axungu.platform.core.model.SystemOauthUserPassword;

import java.util.List;

/**
 * @author Administrator
 * @date 2017-6-5
 */
public interface SystemOauthUserInfoService {

    /**
     * 查询用户单个的登录账号
     *
     * @param loginAccount
     * @param accountType
     * @return
     */
    SystemOauthUserLoginAccount findLoginAccount(String loginAccount, AccountType accountType);

    /**
     * 查询用户基本信息
     *
     * @param id
     * @return
     */
    SystemOauthUserBaseInfo findUserBaseInfoById(Long id);

    /**
     * 查询用户登录密码
     *
     * @param uid
     * @param type
     * @return
     */
    SystemOauthUserPassword findUserPasswd(Long uid, UserPasswordType type);

    /**
     * 插入一条登录记录
     *
     * @param uid
     * @param ip
     */
    void updateLoginInfo(Long uid, String ip);

    /**
     * 查询用户最后一次登录
     *
     * @param uid
     * @return
     */
    SystemOauthUserLoginLog findLastLogin(Long uid);


    /**
     * 查询用户所拥有的权限
     *
     * @param uid
     * @return
     */
    List<String> findAuthorities(Long uid);


}
