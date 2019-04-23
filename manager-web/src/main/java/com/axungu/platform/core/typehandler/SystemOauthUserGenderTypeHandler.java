package com.axungu.platform.core.typehandler;

import com.axungu.common.AbstractEnumTypeHandler;
import com.axungu.platform.core.model.SystemOauthUserBaseInfo;

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