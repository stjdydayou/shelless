package com.axungu.common.oauth;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Permission {

    @AliasFor("authority")
    String[] value() default {};

    /**
     * 权限
     *
     * @return
     */
    @AliasFor("value")
    String[] authority() default {};

    /**
     * 所在插件
     *
     * @return
     */
    String pluginKey();

    /**
     * 所在模块
     *
     * @return
     */
    String moduleKey();
}
