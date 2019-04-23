package com.dliyun.platform.common.validation;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2018/12/20 13:32
 */

import com.dliyun.platform.common.validation.constraints.MobileNumberValidate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {MobileNumberValidate.class})
public @interface MobileNumber {
    //默认错误消息
    String message() default "手机号不合法";

    //分组
    Class<?>[] groups() default {};

    //负载
    Class<? extends Payload>[] payload() default {};

    //指定多个时使用
    @Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        MobileNumber[] value();
    }
}