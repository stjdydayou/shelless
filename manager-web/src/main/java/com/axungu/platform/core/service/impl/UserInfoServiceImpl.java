package com.axungu.platform.core.service.impl;

import com.axungu.common.utils.DateUtil;
import com.axungu.platform.core.enums.AccountType;
import com.axungu.platform.core.enums.UserPasswordType;
import com.axungu.platform.core.mappers.UserInfoMapper;
import com.axungu.platform.core.model.UserBaseInfo;
import com.axungu.platform.core.model.UserLoginAccount;
import com.axungu.platform.core.model.UserLoginLog;
import com.axungu.platform.core.model.UserPassword;
import com.axungu.platform.core.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author jtoms
 */
@Slf4j
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public UserLoginAccount findLoginAccount(String loginAccount, AccountType accountType) {
        return this.userInfoMapper.findLoginAccount(loginAccount, accountType);
    }

    @Override
    public UserBaseInfo findUserBaseInfoById(Long id) {
        return this.userInfoMapper.findUserBaseInfoById(id);
    }

    @Override
    public UserPassword findUserPasswd(Long uid, UserPasswordType type) {
        return this.userInfoMapper.findUserPasswd(uid, type);
    }


    @Override
    public void updateLoginInfo(Long uid, String lastLoginIp) {
        this.threadPoolTaskExecutor.execute(() -> {
            Exception exp = transactionTemplate.execute(status -> {
                try {
                    UserLoginLog loginLog = new UserLoginLog();
                    try {

                    } catch (Exception e) {
                        log.warn("iplookup error ");
                    }
                    loginLog.setLoginTime(DateUtil.current());
                    loginLog.setLoginIp(lastLoginIp);
                    loginLog.setUid(uid);
                    userInfoMapper.insertLoginLog(loginLog);

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
    public UserLoginLog findLastLogin(Long uid) {
        return this.userInfoMapper.findLastLogin(uid);
    }
}
