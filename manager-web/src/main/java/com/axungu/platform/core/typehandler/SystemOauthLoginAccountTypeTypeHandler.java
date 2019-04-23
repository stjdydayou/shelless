package com.axungu.platform.core.typehandler;

import com.axungu.common.AbstractEnumTypeHandler;
import com.axungu.platform.core.model.SystemOauthUserLoginAccount;

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