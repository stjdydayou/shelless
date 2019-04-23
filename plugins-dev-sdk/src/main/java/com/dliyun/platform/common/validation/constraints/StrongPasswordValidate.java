package com.dliyun.platform.common.validation.constraints;

import com.dliyun.platform.common.utils.PatternUtils;
import com.dliyun.platform.common.validation.StrongPassword;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2018/12/20 13:40
 */
public class StrongPasswordValidate implements ConstraintValidator<StrongPassword, String> {
    @Override
    public void initialize(StrongPassword mobileNumber) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.isBlank(value) || PatternUtils.validExpression(value, PatternUtils.STRONG_PASSWORD_EXPRESSION);
    }
}