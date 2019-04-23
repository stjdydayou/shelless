package com.dliyun.platform.core.mappers;

import com.dliyun.platform.common.paginator.domain.PageBounds;
import com.dliyun.platform.common.paginator.domain.PageList;
import com.dliyun.platform.core.model.SystemOauthUserBaseInfo;
import com.dliyun.platform.core.model.SystemOauthUserLoginAccount;
import com.dliyun.platform.core.model.SystemOauthUserLoginLog;
import com.dliyun.platform.core.model.SystemOauthUserPassword;
import com.dliyun.platform.core.vo.SystemOauthUserInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Administrator
 * @date 2017-6-7
 */
public interface SystemOauthUserInfoMapper {

    /**
     * 分页查询用户列表
     *
     * @param bounds
     * @param vo
     * @return
     */
    PageList<SystemOauthUserBaseInfo> findPage(PageBounds bounds, SystemOauthUserInfoVO vo);

    /**
     * 通过用户账号与账号类型查询唯一登录账号
     *
     * @param loginAccount
     * @param accountType
     * @return
     */
    SystemOauthUserLoginAccount findLoginAccount(@Param("loginAccount") String loginAccount, @Param("accountType") SystemOauthUserLoginAccount.AccountType accountType);

    /**
     * 查询一个用户的账号详情
     *
     * @param id
     * @return
     */
    SystemOauthUserBaseInfo findUserBaseInfoById(@Param("id") Long id);

    /**
     * 查询用户的密码
     *
     * @param uid
     * @param type
     * @return
     */
    SystemOauthUserPassword findUserPasswd(@Param("uid") Long uid, @Param("type") SystemOauthUserPassword.UserPasswordType type);

    /**
     * 插入用户的登录日志
     *
     * @param loginLog
     */
    void insertLoginLog(SystemOauthUserLoginLog loginLog);

    /**
     * 查询用户最后一次登录信息
     *
     * @param uid
     * @return
     */
    SystemOauthUserLoginLog findLastLogin(@Param("uid") Long uid);

    /**
     * 查询用户所拥有的权限
     *
     * @param uid
     * @return
     */
    List<String> findAuthorities(@Param("uid") Long uid);

    /**
     * 查询用户所有登录账号
     *
     * @param uid
     * @return
     */
    List<Long> findRolesIdByUserId(@Param("uid") Long uid);


    /**
     * 查询用户所有的角色
     *
     * @param uid
     * @return
     */
    List<SystemOauthUserLoginAccount> findLoginAccountsByUid(@Param("uid") Long uid);

    /**
     * 插入用户基本信息
     *
     * @param userInfo
     */
    void insertBaseInfo(SystemOauthUserBaseInfo userInfo);

    /**
     * 更新用户基本信息
     *
     * @param userBaseInfo
     */
    void updateBaseInfo(SystemOauthUserBaseInfo userBaseInfo);

    /**
     * 更新用户密码
     *
     * @param userPassword
     */
    void insertOrUpdateUserPassword(SystemOauthUserPassword userPassword);

    /**
     * 删除用户下的角色
     *
     * @param uid
     */
    void deleteRoles(@Param("uid") Long uid);

    /**
     * 保存用户角色
     *
     * @param uid
     * @param roleIds
     */
    void insertRoles(@Param("uid") Long uid, @Param("roleIds") Long[] roleIds);

    /**
     * 插入或者更新用户账号
     *
     * @param loginAccount
     */
    void insertOrUpdateLoginAccount(SystemOauthUserLoginAccount loginAccount);
}
