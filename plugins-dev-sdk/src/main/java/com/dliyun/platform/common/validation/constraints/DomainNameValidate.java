package com.dliyun.platform.common.validation.constraints;

import com.dliyun.platform.common.utils.PatternUtils;
import com.dliyun.platform.common.validation.DomainName;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2018/12/20 13:40
 */
public class DomainNameValidate implements ConstraintValidator<DomainName, String> {
    @Override
    public void initialize(DomainName domainName) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.isBlank(value) || PatternUtils.validExpression(value, PatternUtils.DOMAIN_NAME_EXPRESSION);
    }
}