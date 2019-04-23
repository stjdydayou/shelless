package com.dliyun.platform.core.typehandler;

import com.dliyun.platform.common.AbstractEnumTypeHandler;
import com.dliyun.platform.core.model.SystemOauthUserBaseInfo;

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