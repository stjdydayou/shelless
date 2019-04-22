package com.axungu.platform.core.service;

import com.axungu.platform.core.enums.AccountType;
import com.axungu.platform.core.enums.UserPasswordType;
import com.axungu.platform.core.model.UserBaseInfo;
import com.axungu.platform.core.model.UserLoginAccount;
import com.axungu.platform.core.model.UserLoginLog;
import com.axungu.platform.core.model.UserPassword;

/**
 * @author Administrator
 * @date 2017-6-5
 */
public interface UserInfoService {

    /**
     * 查询用户单个的登录账号
     *
     * @param loginAccount
     * @param accountType
     * @return
     */
    UserLoginAccount findLoginAccount(String loginAccount, AccountType accountType);

    /**
     * 查询用户基本信息
     *
     * @param id
     * @return
     */
    UserBaseInfo findUserBaseInfoById(Long id);

    /**
     * 查询用户登录密码
     *
     * @param uid
     * @param type
     * @return
     */
    UserPassword findUserPasswd(Long uid, UserPasswordType type);

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
    UserLoginLog findLastLogin(Long uid);

}
