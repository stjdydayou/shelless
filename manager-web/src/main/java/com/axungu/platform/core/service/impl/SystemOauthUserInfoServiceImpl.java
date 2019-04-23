package com.axungu.platform.core.service.impl;

import com.axungu.common.utils.DateUtil;
import com.axungu.platform.core.enums.AccountType;
import com.axungu.platform.core.enums.UserPasswordType;
import com.axungu.platform.core.mappers.SystemOauthUserInfoMapper;
import com.axungu.platform.core.model.SystemOauthUserBaseInfo;
import com.axungu.platform.core.model.SystemOauthUserLoginAccount;
import com.axungu.platform.core.model.SystemOauthUserLoginLog;
import com.axungu.platform.core.model.SystemOauthUserPassword;
import com.axungu.platform.core.service.SystemOauthUserInfoService;
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
    public SystemOauthUserLoginAccount findLoginAccount(String loginAccount, AccountType accountType) {
        return this.systemOauthUserInfoMapper.findLoginAccount(loginAccount, accountType);
    }

    @Override
    public SystemOauthUserBaseInfo findUserBaseInfoById(Long id) {
        return this.systemOauthUserInfoMapper.findUserBaseInfoById(id);
    }

    @Override
    public SystemOauthUserPassword findUserPasswd(Long uid, UserPasswordType type) {
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
}
