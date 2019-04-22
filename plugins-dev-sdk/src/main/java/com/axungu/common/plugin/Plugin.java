package com.axungu.common.plugin;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2018/12/6 15:55
 */
@Component
@Configuration
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Plugin {
    @AliasFor("key")
    String value() default "";

    @AliasFor("value")
    String key() default "";

    String name();

    String faicon();
}
