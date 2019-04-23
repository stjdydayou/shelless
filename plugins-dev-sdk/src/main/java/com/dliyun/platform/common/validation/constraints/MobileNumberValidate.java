package com.dliyun.platform.common.validation.constraints;

import com.dliyun.platform.common.utils.PatternUtils;
import com.dliyun.platform.common.validation.MobileNumber;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2018/12/20 13:40
 */
public class MobileNumberValidate implements ConstraintValidator<MobileNumber, String> {
    @Override
    public void initialize(MobileNumber mobileNumber) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.isBlank(value) || PatternUtils.validExpression(value, PatternUtils.MOBILE_EXPRESSION);
    }
}