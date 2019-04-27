package com.dliyun.platform.core.typehandler;

import com.dliyun.platform.common.AbstractEnumTypeHandler;
import com.dliyun.platform.core.model.SystemOauthUserLoginAccount;

/**
 * @author jtoms
 */
public class SystemOauthLoginAccountTypeTypeHandler extends AbstractEnumTypeHandler<SystemOauthUserLoginAccount.AccountType> {

    @Override
    protected SystemOauthUserLoginAccount.AccountType codeToEnum(int code) {
        return SystemOauthUserLoginAccount.AccountType.valueOf(code);
    }

    @Override
    protected int enumTocode(SystemOauthUserLoginAccount.AccountType parameter) {
        return parameter.getCode();
    }
}