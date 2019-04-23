package com.dliyun.platform.core.service.impl;

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
    public void insertOrUpdateUserPassword(Long uid, String passwd, String salt, SystemOauthUserPassword.UserPasswordType type) {
        SystemOauthUserPassword userPassword = new SystemOauthUserPassword();
        userPassword.setUid(uid);
        userPassword.setPasswd(passwd);
        userPassword.setSalt(salt);
        userPassword.setType(type);
        this.systemOauthUserInfoMapper.insertOrUpdateUserPassword(userPassword);
    }
}
