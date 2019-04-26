package com.dliyun.platform.core.service.impl;

import com.dliyun.platform.common.exception.ServiceException;
import com.dliyun.platform.common.paginator.domain.PageBounds;
import com.dliyun.platform.common.paginator.domain.PageResult;
import com.dliyun.platform.common.utils.DateUtil;
import com.dliyun.platform.core.mappers.SystemOauthUserInfoMapper;
import com.dliyun.platform.core.model.SystemOauthUserBaseInfo;
import com.dliyun.platform.core.model.SystemOauthUserLoginAccount;
import com.dliyun.platform.core.model.SystemOauthUserLoginLog;
import com.dliyun.platform.core.model.SystemOauthUserPassword;
import com.dliyun.platform.core.service.SystemOauthUserInfoService;
import com.dliyun.platform.core.vo.SystemOauthUserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

/**
 * @author jtoms
 */
@Slf4j
@Service
public class SystemOauthUserInfoServiceImpl implements SystemOauthUserInfoService {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private SystemOauthUserInfoMapper systemOauthUserInfoMapper;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public PageResult<SystemOauthUserBaseInfo> findPage(PageBounds bounds, SystemOauthUserInfoVO vo) {
        return this.systemOauthUserInfoMapper.findPage(bounds, vo).getPageResult();
    }

    @Override
    public SystemOauthUserLoginAccount findLoginAccount(String loginAccount, SystemOauthUserLoginAccount.AccountType accountType) {
        return this.systemOauthUserInfoMapper.findLoginAccount(loginAccount, accountType);
    }

    @Override
    public List<SystemOauthUserLoginAccount> findLoginAccountsByUid(Long uid) {
        return this.systemOauthUserInfoMapper.findLoginAccountsByUid(uid);
    }

    @Override
    public SystemOauthUserBaseInfo findUserBaseInfoById(Long id) {
        return this.systemOauthUserInfoMapper.findUserBaseInfoById(id);
    }

    @Override
    public SystemOauthUserPassword findUserPasswd(Long uid, SystemOauthUserPassword.UserPasswordType type) {
        return this.systemOauthUserInfoMapper.findUserPasswd(uid, type);
    }


    @Override
    public void updateLoginInfo(Long uid, String lastLoginIp) {
        this.threadPoolTaskExecutor.execute(() -> {
            Exception exp = transactionTemplate.execute(status -> {
                try {
                    SystemOauthUserLoginLog loginLog = new SystemOauthUserLoginLog();
                    try {

                    } catch (Exception e) {
                        log.warn("iplookup error ");
                    }
                    loginLog.setLoginTime(DateUtil.current());
                    loginLog.setLoginIp(lastLoginIp);
                    loginLog.setUid(uid);
                    systemOauthUserInfoMapper.insertLoginLog(loginLog);

                    return null;
                } catch (Exception e) {
                    status.setRollbackOnly();
                    return e;
                }
            });
            if (exp != null) {
                log.warn("updateLoginInfo error", exp);
            }
        });
    }

    @Override
    public SystemOauthUserLoginLog findLastLogin(Long uid) {
        return this.systemOauthUserInfoMapper.findLastLogin(uid);
    }

    @Override
    public List<String> findAuthorities(Long uid) {
        return this.systemOauthUserInfoMapper.findAuthorities(uid);
    }

    @Override
    public List<Long> findRolesIdByUserId(Long uid) {
        return this.systemOauthUserInfoMapper.findRolesIdByUserId(uid);
    }

    @Override
    public void updateBaseInfo(SystemOauthUserBaseInfo userBaseInfo) {
        this.systemOauthUserInfoMapper.updateBaseInfo(userBaseInfo);
    }

    @Override
    public void updateBaseInfo(SystemOauthUserBaseInfo userBaseInfo, List<SystemOauthUserLoginAccount> loginAccounts) throws ServiceException {
        Exception exception = this.transactionTemplate.execute(status -> {
            try {

                systemOauthUserInfoMapper.updateBaseInfo(userBaseInfo);

                for (SystemOauthUserLoginAccount loginAccount : loginAccounts) {
                    loginAccount.setUid(userBaseInfo.getId());
                    systemOauthUserInfoMapper.deleteLoginAccount(loginAccount.getId());
                    systemOauthUserInfoMapper.insertOrUpdateLoginAccount(loginAccount);
                }
            } catch (Exception e) {
                status.setRollbackOnly();
                return e;
            }
            return null;
        });

        if (exception != null) {
            throw new ServiceException(exception);
        }
    }

    @Override
    public void insertOrUpdateUserPassword(Long uid, String password, String salt, SystemOauthUserPassword.UserPasswordType type) {
        SystemOauthUserPassword userPassword = SystemOauthUserPassword.instance(uid, password, salt, type);
        this.systemOauthUserInfoMapper.insertOrUpdateUserPassword(userPassword);
    }

    @Override
    public void saveRoles(Long uid, Long[] roleIds) throws ServiceException {
        Exception exp = this.transactionTemplate.execute(status -> {
            try {
                systemOauthUserInfoMapper.deleteRoles(uid);
                if (roleIds != null && roleIds.length > 0) {
                    systemOauthUserInfoMapper.insertRoles(uid, roleIds);
                }
                return null;
            } catch (Exception e) {
                status.setRollbackOnly();
                return e;
            }

        });

        if (exp != null) {
            throw new ServiceException(exp);
        }
    }

    @Override
    public void register(SystemOauthUserBaseInfo userInfo, String loginPassword, String salt, List<SystemOauthUserLoginAccount> loginAccounts) throws ServiceException {
        Exception exception = this.transactionTemplate.execute(status -> {
            try {
                if (StringUtils.isBlank(userInfo.getAvatar())) {
                    userInfo.setAvatar("/defultAvatar.jpg");
                }

                systemOauthUserInfoMapper.insertBaseInfo(userInfo);

                systemOauthUserInfoMapper.insertOrUpdateUserPassword(SystemOauthUserPassword.instance(userInfo.getId(), loginPassword, salt, SystemOauthUserPassword.UserPasswordType.login));


                for (SystemOauthUserLoginAccount loginAccount : loginAccounts) {
                    loginAccount.setUid(userInfo.getId());
                    systemOauthUserInfoMapper.insertOrUpdateLoginAccount(loginAccount);
                }
            } catch (Exception e) {
                status.setRollbackOnly();
                return e;
            }
            return null;
        });

        if (exception != null) {
            throw new ServiceException(exception);
        }
    }
}
