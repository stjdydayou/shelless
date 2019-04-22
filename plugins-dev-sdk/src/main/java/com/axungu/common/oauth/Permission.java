package com.axungu.common.oauth;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Permission {

    @AliasFor("authority")
    String[] value() default {};

    // 所在模块
    @AliasFor("value")
    String[] authority() default {};
}
