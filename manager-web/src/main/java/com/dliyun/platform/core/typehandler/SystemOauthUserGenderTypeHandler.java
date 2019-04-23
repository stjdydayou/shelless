package com.dliyun.platform.core.typehandler;

import com.dliyun.platform.common.AbstractEnumTypeHandler;
import com.dliyun.platform.core.model.SystemOauthUserBaseInfo;

/**
 * @author jtoms
 */
public class SystemOauthUserGenderTypeHandler extends AbstractEnumTypeHandler<SystemOauthUserBaseInfo.Gender> {

    @Override
    protected SystemOauthUserBaseInfo.Gender codeToEnum(int code) {
        return SystemOauthUserBaseInfo.Gender.valueOf(code);
    }

    @Override
    protected int enumTocode(SystemOauthUserBaseInfo.Gender parameter) {
        return parameter.getCode();
    }

}