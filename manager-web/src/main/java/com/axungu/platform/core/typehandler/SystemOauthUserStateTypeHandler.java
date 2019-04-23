package com.axungu.platform.core.typehandler;

import com.axungu.common.AbstractEnumTypeHandler;
import com.axungu.platform.core.model.SystemOauthUserBaseInfo;

/**
 * @author jtoms
 */
public class SystemOauthUserStateTypeHandler extends AbstractEnumTypeHandler<SystemOauthUserBaseInfo.UserState> {

    @Override
    protected SystemOauthUserBaseInfo.UserState codeToEnum(int code) {
        return SystemOauthUserBaseInfo.UserState.valueOf(code);
    }

    @Override
    protected int enumTocode(SystemOauthUserBaseInfo.UserState parameter) {
        return parameter.getCode();
    }
}