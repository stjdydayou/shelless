package com.axungu.platform.core.mappers;

import com.axungu.platform.core.enums.AccountType;
import com.axungu.platform.core.enums.UserPasswordType;
import com.axungu.platform.core.model.UserBaseInfo;
import com.axungu.platform.core.model.UserLoginAccount;
import com.axungu.platform.core.model.UserLoginLog;
import com.axungu.platform.core.model.UserPassword;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017-6-7.
 */
public interface UserInfoMapper {
    /**
     * 通过用户账号与账号类型查询唯一登录账号
     *
     * @param loginAccount
     * @param accountType
     * @return
     */
    UserLoginAccount findLoginAccount(@Param("loginAccount") String loginAccount, @Param("accountType") AccountType accountType);

    /**
     * 查询一个用户的账号详情
     *
     * @param id
     * @return
     */
    UserBaseInfo findUserBaseInfoById(@Param("id") Long id);

    /**
     * 查询用户的密码
     *
     * @param uid
     * @param type
     * @return
     */
    UserPassword findUserPasswd(@Param("uid") Long uid, @Param("type") UserPasswordType type);

    /**
     * 插入用户的登录日志
     *
     * @param loginLog
     */
    void insertLoginLog(UserLoginLog loginLog);

    /**
     * 查询用户最后一次登录信息
     *
     * @param uid
     * @return
     */
    UserLoginLog findLastLogin(Long uid);

    /**
     * 查询用户所拥有的权限
     *
     * @param uid
     * @return
     */
    List<String> findAuthorities(Long uid);
}
