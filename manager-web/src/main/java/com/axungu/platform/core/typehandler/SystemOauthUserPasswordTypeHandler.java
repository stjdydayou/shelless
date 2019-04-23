package com.axungu.platform.core.typehandler;

import com.axungu.common.AbstractEnumTypeHandler;
import com.axungu.platform.core.model.SystemOauthUserPassword;

/**
 * @author jtoms
 */
public class SystemOauthUserPasswordTypeHandler extends AbstractEnumTypeHandler<SystemOauthUserPassword.UserPasswordType> {

    @Override
    protected SystemOauthUserPassword.UserPasswordType codeToEnum(int code) {
        return SystemOauthUserPassword.UserPasswordType.valueOf(code);
    }

    @Override
    protected int enumTocode(SystemOauthUserPassword.UserPasswordType parameter) {
        return parameter.getCode();
    }
}