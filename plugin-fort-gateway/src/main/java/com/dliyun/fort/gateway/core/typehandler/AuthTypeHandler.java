package com.dliyun.fort.gateway.core.typehandler;

import com.dliyun.fort.gateway.core.model.HostAuth;
import com.dliyun.platform.common.AbstractEnumTypeHandler;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/3/21 20:36
 */
public class AuthTypeHandler extends AbstractEnumTypeHandler<HostAuth.AuthType> {


    @Override
    protected HostAuth.AuthType codeToEnum(int code) {
        return HostAuth.AuthType.valueOf(code);
    }

    @Override
    protected int enumTocode(HostAuth.AuthType parameter) {
        return parameter.getCode();
    }
}